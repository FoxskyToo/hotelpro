# HotelPro 

# Integrantes:

- Boris Pérez
- Bastian Ampuero

# Descripción del Proyecto

HotelPro es una aplicación backend desarrollada con Spring Boot que implementa microservicios para la administración de un sistema hotelero.

# Funcionalidades

- Autenticación mediante token
- CRUD de usuarios
- CRUD de clientes
- CRUD de habitaciones
- CRUD de reservas
- Validaciones DTO
- Manejo centralizado de errores
- Logs con SLF4J
- Persistencia con JPA/Hibernate
- Comunicación REST entre módulos

# Herramientas Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate
- MySQL
- Maven
- Lombok
- Postman

# Pasos para Ejecutar



# 1 Crear base de datos:

```sql
CREATE DATABASE hotelpro;
```

# 2 Configurar application.properties:

```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

# 3 Ejecutar proyecto:

Entrar en la carpeta del proyecto y dirigirse a ("/duoc.hotelpro"), y dirigirse a ("HotelproApplication.java").



# 4. Acceder al backend


Abrir postman y ingresar ("http://localhost:8080/api/"), con la dirección a la cual desea ir


# Seguridad

La aplicación utiliza autenticación mediante token:
Authorization: Bearer TOKEN


# Endpoints Principales

# Usuarios
- /api/usuarios

# Clientes
- /api/clientes

# Habitaciones
- /api/habitaciones

# Reservas
- /api/reservas
