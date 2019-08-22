# java-notes
Template for Simple Java Spring Web App
- JSP
- Swagger Rest
- PostgreSql
- Hibernate / JPA + Repositories
- Checkstyle
- JUnit + POJO Tester
- Jacoco Coverage
- Docker image

## Release Notes
- Back end and front end separated for be close to production like web application.
- PostgreSQL instead of H2 have been chosen, due to it's more production like solution.
- REST API's and POJO's classes covered with tests (coverage 90%).
- Simple UI for notes CRUD managment
- DB configuration inside POM

## Requirements
- Apache Maven 3.6.0 (maven wrapper included into project, `./mvnw`)
- Java version: 1.8 (tested with `1.8.0_131`)

## Run application
- from local environment:
  - PostgreSQL server should be up and running on `localhost:5432`, tested with `9.5 and 11.5 version`.
  - Run command:
    - `./mvnw clean install && ./mvnw spring-boot:run`.
  - Run with skip tests and checks mode: 
    - `./mvnw clean install -DskipTests && ./mvnw spring-boot:run -DskipTests`.
  - Open server URL: [http://localhost:9090](http://localhost:9090).
- via Docker containers:
  - Docker Compose should be installed. Tested with:
    - Docker version `18.09.3`
    - docker-compose version `1.23.2`, build 1110ad01.
  - Run command (Linux):
    - `./docker.sh start` for starting docker environment
    - `./docker.sh stop` for stopping docker environment.
  - You are welcome to review `./docker` script source and repeat steps manually or write similar launch script for any another OS.
  - Open server URL: [http://localhost:9090](http://localhost:9090).

   
