# Tenpo challenge

Autor: Jesus ledesma

Linkedin: https://www.linkedin.com/in/jesusledesma/

Medium: https://jesusledesma.medium.com/

Canal de Youtube: https://www.youtube.com/@jesusledesma895/videos

### Tecnologias

- Java 11
- Spring Boot 2.7.11
- Spring Jpa
- Spring Data Redis
- Spring Kafka
- Postgres
- Redis
- Kafka
- Junit 5 + Mockito
- Spring Swagger y Open API
- Spring retry
- Maven
- Docker

### Inicio de app

Para iniciar la aplicacion ejecutar

`docker-compose up`

(Opcional) Si ademas se quiere ver los logs

`docker-compose up && docker-compose logs -f`

(Opcional) Si se quiere eliminar los containers previos antes de iniciar el docker compose

` docker rm -f $(docker ps -aq)`

### Arquitectura
La aplicacion esta estructurada con Clean-Architecture orientada a DDD

Esta decision permite mayor flexibidad, desacople de las tecnologias antes mencionadas y cohesion 
al punto de que podriamos cambiar el framework, las bases de datos y el broker de mensajeria sin afectar el modelo de nuestro sistema

La estructuracion de paquete es orientada a conceptos del dominio y luego en el tipo de componente

Comparto una charla tech que realice para una empresa para mas informacion
https://youtu.be/qcDfwMF3hHI

### API

- En la raiz del proyecto esta la collection de postman
- Se puede acceder a swagger a traves de http://localhost:8080/challenge/swagger-ui/index.html


####/operations/addition
Request:
```
{
    "value1": 5,
    "value2": 5
}
```

Response:
```
{
    "value1": 5,
    "value2": 5,
    "percentage": 21,
    "total": 12.1
}
```

####/records?pageNumber=0&pageSize=5
- pageNumber default = 0
- pageSize = 5

Response:
```
{
    "results": [
        {
            "id": "1823f387-1216-4068-a0d9-31960fa0e9d8",
            "endpoint": "/challenge/api/v1/operations/addition",
            "creationDate": "2023-05-07T22:22:41.267598",
            "responseCode": 200,
            "responseStatus": "200 OK",
            "response": "\"{\\\"value1\\\":5,\\\"value2\\\":5,\\\"percentage\\\":67,\\\"total\\\":16.7}\""
        },
        {
            "id": "3fc5f801-b345-49b5-8b75-92b1355355b0",
            "endpoint": "/challenge/api/v1/operations/addition",
            "creationDate": "2023-05-07T22:22:44.313212",
            "responseCode": 429,
            "responseStatus": "429 TOO_MANY_REQUESTS"
        },
        {
            "id": "eb58c776-e174-4d11-a467-f65f74dc51cf",
            "endpoint": "/challenge/api/v1/records",
            "creationDate": "2023-05-07T23:33:26.044023",
            "responseCode": 200,
            "responseStatus": "200 OK",
            "response": "\"{\\\"results\\\":[{\\\"id\\\":\\\"22ad737f-2fea-4fff-a25d-aeb2ab41086a\\\",\\\"endpoint\\\":\\\"/challenge/api/v1/operations/addition\\\",\\\"creationDate\\\":\\\"2023-05-07T23:33:21.057357\\\",\\\"responseCode\\\":200,\\\"responseStatus\\\":\\\"200 OK\\\",\\\"response\\\":\\\"\\\\\\\"{\\\\\\\\\\\\\\\"value1\\\\\\\\\\\\\\\":5,\\\\\\\\\\\\\\\"value2\\\\\\\\\\\\\\\":5,\\\\\\\\\\\\\\\"percentage\\\\\\\\\\\\\\\":77,\\\\\\\\\\\\\\\"total\\\\\\\\\\\\\\\":17.7}\\\\\\\"\\\"}],\\\"totalElements\\\":1,\\\"pageNumber\\\":0,\\\"pageSize\\\":5}\""
        }
    ],
    "totalElements": 3,
    "pageNumber": 0,
    "pageSize": 5
}
```

### Cache y Servicio externo
El servicio externo es:
http://www.randomnumberapi.com/api/v1.0/random?min=1&max=100&count=1

Hay varios requerimientos involucrados:
- No consumir el servicio externo siempre, debido a que se actuliza poco.
  Se debe hacer cada 30 minutos
- Poder realizar reintentos en caso de que falle el servicio externo
- En caso de que el servicio externo falle, luego de los reintentos retornar el ultimo valor usado
- Poder mockear el servicio externo

Para ello se utiliza una cache. La base de datos es Redis (Por clean architecture puede ser cambiar con facilidad)

Los requerimientos son configurables
```
retries = 3
retrydelay = 1000
cachedseconds = 1800
mock = false
```

### Eventos y Filter
Se pide como requerimiento guardar el historial de todos los endpoint de sistema sin que: 
- Sume tiempo al servicio invocado
- Un error afecte al servicio invocado

Debido a que la logica se repite en todos los endpoint de opto por utilizar un filter para evitar repeticio de codigo

Debido a que un error al lanzar un hilo puede evitar que se persista el historico, 
se opto por enviar un evento por medio de kakfa (Programado de una forma que puede ser intercambiado por cualquier broker).
El evento es consumido y persiste el historico

### Rate Limiter
Se establecio como requerimiento que la API de suma debe recibir como maximo de 3 rpm 

Para resolverlo:
- Se programo una anottation @RateLimited, donde se puede configurar la cantidad de request y en un determinado tiempo
- La anotation permite una configuracion granular por endpoint
- La solucion es **Distribuida**
- Se utilizo una base de datos Redis para de forma atomica contar las request 
(Esta programado de forma que pueda ser remplazada por cualquier otra base con atomocidad) 
- Se Utilizo un interceptor y reflection para verificar la anotation en cada request que entra a un endpoint

### Posibles Mejoras
1) La solcucion podria lograr una performance como si se usara GO. 
Se puede realizar combinando programacion reactiva (Webflux) + Corrutinas (Kotlin) + Clean Architecture. 
Comparto post que publique respecto al tema 
https://jesusledesma.medium.com/migrando-a-clean-architecture-webflux-con-corrutinas-en-kotlin-parte-1-ec382b933438 
2) Test de Integracion y End-to-End
3) Guardar contraseñas encryptadas o en un vault