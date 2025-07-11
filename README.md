# 🏗️ Sistema de Gestión de Materiales - Backend

## 📋 Descripción

API RESTful desarrollada en Spring Boot para la gestión de materiales, ciudades y departamentos. Este microservicio forma parte de un sistema de gestión integral que permite realizar operaciones CRUD sobre materiales, así como búsquedas avanzadas por diferentes criterios.

## 🚀 Características Principales

- ✅ **CRUD completo** de materiales
- ✅ **Búsquedas avanzadas** por tipo, fecha de compra, ciudad y departamento
- ✅ **Autenticación JWT** integrada con microservicio de autenticación
- ✅ **Validaciones de negocio** (fechas de compra/venta)
- ✅ **Documentación automática** con Swagger/OpenAPI
- ✅ **Manejo de errores** estandarizado
- ✅ **Logging completo** para auditoría
- ✅ **Base de datos** H2 (desarrollo) y PostgreSQL (producción)

## 🛠️ Tecnologías

- **Java 17+**
- **Spring Boot 3.4.5**
- **Spring Security** con JWT
- **Spring Data JPA**
- **H2 Database** (desarrollo)
- **PostgreSQL** (producción)
- **Swagger/OpenAPI 3**
- **Maven**

## 📁 Estructura del Proyecto

```
src/main/java/com/materials/management/
├── MaterialsManagementApplication.java     # Clase principal
├── client/                                 # Cliente para microservicio auth
│   └── AuthClient.java
├── config/                                 # Configuraciones
│   ├── CorsConfig.java
│   ├── RestTemplateConfig.java
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
├── controller/                             # Controladores REST
│   ├── CityController.java
│   ├── DepartmentController.java
│   └── MaterialController.java
├── dto/                                    # DTOs de transferencia
│   ├── request/
│   │   ├── MaterialRequestDto.java
│   │   └── MaterialSearchDto.java
│   └── response/
│       ├── ApiResponseDto.java
│       ├── CityResponseDto.java
│       ├── DepartmentResponseDto.java
│       └── MaterialResponseDto.java
├── entity/                                 # Entidades JPA
│   ├── City.java
│   ├── Department.java
│   └── Material.java
├── enums/                                  # Enumeraciones
│   ├── MaterialStatus.java
│   └── MaterialType.java
├── exception/                              # Manejo de excepciones
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository/                             # Repositorios JPA
│   ├── CityRepository.java
│   ├── DepartmentRepository.java
│   └── MaterialRepository.java
├── security/                               # Seguridad JWT
│   └── JwtAuthenticationFilter.java
└── service/                                # Servicios de negocio
    ├── CityService.java
    ├── DepartmentService.java
    ├── MaterialService.java
    └── impl/
        ├── CityServiceImpl.java
        ├── DepartmentServiceImpl.java
        └── MaterialServiceImpl.java
```

## ⚙️ Configuración

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.8+**
- **Microservicio de autenticación** ejecutándose en `http://localhost:8081`

### Variables de Entorno

```bash
# Desarrollo (opcional - usa valores por defecto)
export DB_USERNAME=sa
export DB_PASSWORD=password

# Producción
export DB_USERNAME=materials_user
export DB_PASSWORD=materials_pass
export AUTH_SERVICE_URL=http://auth-service:8081
```

### Perfiles de Configuración

#### Desarrollo (`development`)
- Base de datos H2 en memoria
- Console H2 habilitada en `/h2-console`
- Logs detallados
- Puerto: `8082`

#### Producción (`production`)
- Base de datos PostgreSQL
- Logs optimizados
- Console H2 deshabilitada

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd materials-management-backend
```

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar tests (opcional)
```bash
mvn test
```

### 4. Ejecutar la aplicación

#### Desarrollo
```bash
mvn spring-boot:run
```

#### Con perfil específico
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=development
```

#### Generar JAR y ejecutar
```bash
mvn clean package -DskipTests
java -jar target/materials-management-0.0.1-SNAPSHOT.jar
```

## 🌐 Endpoints Principales

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| `GET` | `/api/materials` | Obtener todos los materiales | ✅ |
| `GET` | `/api/materials/{id}` | Obtener material por ID | ✅ |
| `POST` | `/api/materials` | Crear nuevo material | ✅ Admin/Manager |
| `PUT` | `/api/materials/{id}` | Actualizar material | ✅ Admin/Manager |
| `DELETE` | `/api/materials/{id}` | Eliminar material | ✅ Admin |
| `GET` | `/api/materials/by-type/{type}` | Buscar por tipo | ✅ |
| `GET` | `/api/materials/by-purchase-date/{date}` | Buscar por fecha compra | ✅ |
| `GET` | `/api/materials/by-city/{cityCode}` | Buscar por ciudad | ✅ |
| `GET` | `/api/materials/search` | Búsqueda avanzada | ✅ |
| `GET` | `/api/cities` | Obtener todas las ciudades | ✅ |
| `GET` | `/api/departments` | Obtener departamentos | ✅ |

## 📚 Documentación

### Swagger UI
- **URL**: `http://localhost:8082/swagger-ui.html`
- **Descripción**: Interfaz interactiva para probar la API

### OpenAPI JSON
- **URL**: `http://localhost:8082/v3/api-docs`
- **Descripción**: Especificación OpenAPI en formato JSON

### Base de Datos H2 Console (solo desarrollo)
- **URL**: `http://localhost:8082/h2-console`
- **JDBC URL**: `jdbc:h2:mem:materialsdb`
- **Usuario**: `sa`
- **Contraseña**: `password`

### Health Check
- **URL**: `http://localhost:8082/actuator/health`
- **Descripción**: Estado de salud de la aplicación

## 🔐 Autenticación

La API utiliza JWT para autenticación. Para acceder a los endpoints protegidos:

1. **Obtener token** del microservicio de autenticación (`http://localhost:8081/api/auth/login`)
2. **Incluir token** en el header: `Authorization: Bearer <token>`

### Roles y Permisos

- **USER**: Consultar materiales
- **MANAGER**: Consultar + Crear + Actualizar materiales
- **ADMIN**: Todas las operaciones + Eliminar

## 📊 Tipos de Material

- `ELECTRONICO` - Equipos electrónicos
- `MECANICO` - Herramientas mecánicas
- `QUIMICO` - Productos químicos
- `TEXTIL` - Materiales textiles
- `CONSTRUCCION` - Materiales de construcción
- `HERRAMIENTA` - Herramientas diversas
- `OFICINA` - Suministros de oficina

## 📋 Estados de Material

- `ACTIVE` - Activo
- `AVAILABLE` - Disponible
- `ASSIGNED` - Asignado
- `INACTIVE` - Inactivo
- `DAMAGED` - Dañado
- `SOLD` - Vendido

## 🧪 Datos de Prueba

La aplicación incluye datos iniciales:
- **10 departamentos** de Colombia
- **25 ciudades** principales
- **15 materiales** de ejemplo

## 🐛 Resolución de Problemas

### Error de conexión con microservicio de autenticación
```bash
# Verificar que el servicio de auth esté ejecutándose
curl http://localhost:8081/api/auth/health
```

### Error de base de datos en producción
```bash
# Verificar conexión PostgreSQL
psql -h localhost -U materials_user -d materialsdb
```

### Puerto ocupado
```bash
# Cambiar puerto en application.yml
server:
  port: 8083
```
