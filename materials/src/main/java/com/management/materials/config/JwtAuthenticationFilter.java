package com.management.materials.config;

import com.management.materials.dto.request.UserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtro JWT que valida tokens contra el microservicio de autenticación
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthClient authClient;

    public JwtAuthenticationFilter(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = extractTokenFromRequest(request);

            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (authClient.validateToken(token)) {
                    UserInfo userInfo = authClient.getUserInfo(token);

                    if (userInfo != null) {
                        setAuthenticationInContext(request, userInfo);
                        logger.debug("Usuario autenticado: {}", userInfo.getUsername());
                    }
                } else {
                    logger.debug("Token inválido o expirado");
                }
            }
        } catch (Exception e) {
            logger.error("Error en filtro JWT: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token del header Authorization
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    /**
     * Establece la autenticación en el contexto de seguridad
     */
    private void setAuthenticationInContext(HttpServletRequest request, UserInfo userInfo) {
        List<SimpleGrantedAuthority> authorities = createAuthorities(userInfo);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userInfo.getUsername(), null, authorities);

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Crea las autoridades basadas en roles y permisos del usuario
     */
    private List<SimpleGrantedAuthority> createAuthorities(UserInfo userInfo) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(userInfo.getRoles())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        // Agregar permisos si existen
        if (userInfo.getPermissions() != null) {
            List<SimpleGrantedAuthority> permissions = Arrays.stream(userInfo.getPermissions())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            authorities.addAll(permissions);
        }

        return authorities;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // No filtrar rutas públicas
        return path.startsWith("/api/public/") ||
                path.startsWith("/actuator/health") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/favicon.ico");
    }
}