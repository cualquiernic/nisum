# Nisum

## Se crean los siguientes servicios asociados al usuario:

### Sin token de autenticación
Nombre: create
Url: localhost:8080/user/create
Tipo: POST

### Requiere token de autenticación

Nombre: getbyemail
Url: localhost:8080/user/getbyemail
Tipo: GET

Nombre: update
Url: localhost:8080/user/update
Tipo: PUT

Nombre: getall
Url: localhost:8080/user/getall
Tipo: GET


Nombre: delete
Url: localhost:8080/user/delete
Tipo: DELETE


## Se crean los siguientes servicios asociados al token:

### Sin token de autenticación

 Nombre: generate
 Url: localhost:8080/jwt/generate
 Descripcion: Valida email y password
 Tipo: POST
 
 
 Nombre: validate
 Url: localhost:8080/jwt/validate
 Tipo: GET
 
 
## Swagger

URL: http://localhost:8080/swagger-ui/index.html

## OpenApi

URL: http://localhost:8080/api-docs

