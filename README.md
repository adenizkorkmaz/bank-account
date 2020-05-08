# Simple bank account application and API.

This is a Java RESTful Web Service ​that you can create account for existing customer and view customer data. 

* After running application you can click below links to see API related documentation:

    1. http://localhost:8080/swagger-ui.html#

    2. http://localhost:8080/v2/api-do

Technologies and libraries are basically like below:

1. `Embeded H2` for database
2. `Spring data and JPA` for persistence and crud support
3. `Spring Hateoas` for link support to response dto
4. `Spring Web` for rest api
5. `Spring ControllerAdvice` for exception handling
6. `Swagger` for API documentation
7. `Junit 5` for tests
8. `Lombok` for data and logging support to get rid of boilerplate code (like getters and setters)
9. `Docker` for running app in container

How to run locally with docker compose : 
1. `./start.sh` --> running tests, building application jar, and start docker-compose
2. `./stop.sh` --> stop docker container

How to run locally with mvn:
1. `mvn clean install` --> run tests and build application artifact
2. `mvn spring-boot:run` --> run application

How to run locally with docker : 
1. `mvn clean install` --> run tests and build application artifact
2. `docker build . -t bank-app-image` --> build docker image
3. `docker run -p 8080:8080  bank-app-image` --> run docker image

