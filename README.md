# SBSA_Assessment1
SBSA Assessment 1

# Requirements
For building and running the application you need:
- JDK 11
- Maven 3+

# Running the application locally
There are several ways to run this application on your local machine.

One way is to execute the `main` method in the `za.co.standardbank.assessment1.SbsaAssessment1Application` class from your preferred IDE.
Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
```shell
mvn spring-boot:run
```
The application will start and run on port 8080 of your local machine.

Another way to run the application is to run the following command from the root of the repository:
```shell
mvn clean install package
java -jar .\target\assessment1-1.0.0-SNAPSHOT.jar
```
The first command will also build the backend and create an artifact (jar file).
The next command starts the application on sport 8080: http://localhost:8080.

The Swagger Documentation of the application will be accessible from this Url: http://localhost:8080/swagger-ui.html


# About this application
This application is a simple Springboot application which submits a json payload to a service.
It also exposed a RESTful API endpoint that a frontend UI will consume to submit a form with firstname, surname, contact number/mobile and email address
When the request to a service fails due to service un-availability/server-side errors, it should store in database/file system and re-submit the application to the service

The application is written in Java 11 with Springboot 2.
When the application starts, it exposes a set of RESTful API which can be accessed, when the application is running locally,
from the link http://localhost:8080/swagger-ui.html

