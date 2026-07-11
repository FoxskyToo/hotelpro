# HotelPro

Sistema de administración hotelera desarrollado como proyecto académico utilizando Spring Boot, arquitectura basada en servicios, autenticación mediante token Bearer y despliegue en la nube.

---

# Integrantes

- Boris Pérez
- Bastian Ampuero

---

# Descripción del Proyecto

HotelPro es una aplicación backend desarrollada en Java 21 utilizando Spring Boot.

El sistema permite administrar los principales procesos de un hotel mediante una API REST protegida con Spring Security.

La solución incorpora un API Gateway, documentación Swagger, persistencia con MySQL, migraciones Flyway y despliegue utilizando Docker, Render y Aiven.

El proyecto puede ejecutarse tanto en un entorno local como en la nube.

---

# Arquitectura

```
                Cliente
      (Postman / Swagger)

               │
               ▼

        HotelPro Gateway

               │
               ▼

          HotelPro API

               │
               ▼

             MySQL
      (Local o Aiven Cloud)
```

La API utiliza una arquitectura por capas:

```
Controller
     │
Service
     │
Repository
     │
MySQL
```

Cada dominio implementa:

- Controller
- Service
- Repository
- Entity
- DTO
- Exception

---

# Funcionalidades

La aplicación permite administrar:

- Autenticación de usuarios
- Usuarios
- Clientes
- Habitaciones
- Reservas
- Pagos
- Servicios
- Consumos
- Empleados
- Mantenimientos

Cada módulo implementa operaciones CRUD.

Además incorpora:

- Autenticación mediante Bearer Token
- API Gateway
- Validaciones mediante DTO
- Manejo centralizado de excepciones
- Logs mediante SLF4J
- Persistencia JPA / Hibernate
- Comunicación mediante WebClient
- Swagger/OpenAPI
- Spring Boot Actuator
- Flyway
- Docker
- Docker Compose

---

# Tecnologías Utilizadas

## Backend

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Spring Validation
- Spring Web MVC
- Spring WebFlux
- Spring Boot Actuator
- WebClient
- Hibernate
- Flyway
- Lombok

## Base de Datos

- MySQL
- Aiven MySQL

## Infraestructura

- Docker
- Docker Compose
- GitHub
- Render

## Testing

- JUnit 5
- Mockito
- Postman

## Documentación

- Swagger / OpenAPI

---

# Estructura del Proyecto

```
HotelProDocker

│
├── hotelpro
│      API principal
│
├── hotelpro-gateway
│      API Gateway
│
├── database
│
├── render
│
├── docker-compose.yml
│
├── render.yaml
│
└── README.md
```

---

# Ejecución Local

## Requisitos

- Java 21
- IntelliJ IDEA
- MySQL (Laragon recomendado)
- Git
- Docker Desktop (opcional)
- Postman

---

## Crear Base de Datos

```sql
CREATE DATABASE hotelpro;
```

La configuración local utiliza:

```
Host: localhost

Puerto: 3306

Base de datos: hotelpro

Usuario: root

Contraseña:
```

---

## Ejecutar API

Abrir el proyecto:

```
hotelpro
```

Ejecutar:

```
HotelproApplication.java
```

Disponible en:

```
http://localhost:8080
```

---

## Ejecutar Gateway

Abrir:

```
hotelpro-gateway
```

Ejecutar:

```
HotelproGatewayApplication.java
```

Disponible en:

```
http://localhost:8091
```

---

# Swagger

Local

```
http://localhost:8080/swagger-ui/index.html
```

Render

```
https://hotelpro-api-ym3x.onrender.com/swagger-ui/index.html
```

Desde Swagger es posible:

- consultar todos los endpoints
- probar operaciones
- visualizar DTO
- autenticarse mediante Bearer Token

---

# Seguridad

La aplicación utiliza autenticación mediante token Bearer.

Login:

```
POST /api/auth/login
```

Ejemplo:

```json
{
    "username":"admin",
    "password":"1234"
}
```

Respuesta:

```json
{
    "tipo":"Bearer",
    "token":"TOKEN"
}
```

Para consumir endpoints protegidos:

```
Authorization:

Bearer TOKEN
```

---

# Usuario de Pruebas

```
Usuario:

admin

Contraseña:

1234
```

Estas credenciales son exclusivamente para demostración.

---

# Health Check

API

```
http://localhost:8080/actuator/health
```

Gateway

```
http://localhost:8091/actuator/health
```

Render

```
https://hotelpro-api-ym3x.onrender.com/actuator/health
```

```
https://hotelpro-gateway.onrender.com/actuator/health
```

Respuesta esperada:

```json
{
    "status":"UP"
}
```

---

# Pruebas Realizadas

Durante el desarrollo fueron validados correctamente los siguientes escenarios.

## Local

API

Gateway

MySQL

Login

Bearer Token

CRUD autenticado

Swagger

Actuator

## Nube

API desplegada en Render

Gateway desplegado en Render

MySQL Aiven

Login

Bearer Token

Endpoint protegido

Swagger

Health Check

---

# Docker

Levantar servicios:

```bash
docker compose up --build
```

Segundo plano

```bash
docker compose up -d
```

Detener

```bash
docker compose down
```

---

# Despliegue Cloud

El proyecto se encuentra preparado para desplegar utilizando:

- GitHub
- Render
- Aiven MySQL

Los despliegues se realizan automáticamente mediante:

```
git push
```

Render detecta los cambios y publica automáticamente los servicios.

---

# Diferencia entre Local y Cloud

## Local

```
Gateway

↓

localhost:8091

↓

API

↓

localhost:8080

↓

MySQL Local
```

## Cloud

```
Gateway

↓

Render

↓

API

↓

Render

↓

Aiven MySQL
```

Las bases de datos funcionan completamente independientes.

---

# Solución de Problemas

## Puerto ocupado

Windows

```powershell
Get-NetTCPConnection -LocalPort 8080
```

Cerrar proceso

```powershell
Stop-Process -Id PID -Force
```

---

## Flyway

Si una migración falla revisar:

```
flyway_schema_history
```

o recrear la base local de pruebas.

---


# Repositorio

https://github.com/FoxskyToo/HotelProDocker

---

# Estado del Proyecto

API completamente funcional

Gateway operativo

Seguridad implementada

CRUD implementados

Swagger

Actuator

Docker

Docker Compose

Render

Aiven

GitHub Actions mediante Auto Deploy de Render
