package com.management.materials.config;


import com.management.materials.dto.request.UserInfo;
import com.management.materials.dto.response.AuthValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Cliente para comunicarse con el microservicio de autenticación
 */
@Service
public class AuthClient {

    private static final Logger logger = LoggerFactory.getLogger(AuthClient.class);

    @Value("${auth.service.url:http://localhost:8081}")
    private String authServiceUrl;

    private final RestTemplate restTemplate;

    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Valida un token JWT contra el microservicio de autenticación
     *
     * @param token Token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean validateToken(String token) {
        try {
            String url = authServiceUrl + "/api/auth/validate";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            logger.debug("Validando token contra: {}", url);

            ResponseEntity<AuthValidationResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    AuthValidationResponse.class
            );

            boolean isValid = response.getStatusCode() == HttpStatus.OK &&
                    response.getBody() != null &&
                    response.getBody().isSuccess();

            if (isValid) {
                logger.debug("Token validado exitosamente");
            } else {
                logger.warn("Token inválido o respuesta inesperada");
            }

            return isValid;

        } catch (Exception e) {
            logger.error("Error al validar token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene información del usuario desde el token
     *
     * @param token Token JWT
     * @return Información del usuario o null si hay error
     */
    public UserInfo getUserInfo(String token) {
        try {
            String url = authServiceUrl + "/api/auth/validate";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<AuthValidationResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    AuthValidationResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK &&
                    response.getBody() != null &&
                    response.getBody().isSuccess()) {

                return response.getBody().getData();
            }

            return null;

        } catch (Exception e) {
            logger.error("Error al obtener información del usuario: {}", e.getMessage());
            return null;
        }
    }



}