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

# About the user registration via API
The application exposes an endpoint to register a user. The endpoint accepts a JSON payload containing the user details (firstname, surname, email, mobileNumber).
When registering a user, there is a validation on the email address checking if it is a valid one and if there is no existing user with the same email address.
If the request passes the validation, then a user is created and saved in state NEW inside an in-memory database H2.

After the user has been saved on the database, that user also needs to be registered to a 3rd party service.
The specification to that 3rd party service have not been provided but our implementation is flexible enough for us to just plug that in once ready.
If the request to that 3rd party is successful, the user is marked as Active in the h2 database.

In case of failure of that 3rd party service, there is a mechanism to save the request or details to reconstitute the request and retry it at a later stage.

There is a database table **ScheduledAction** that holds all the possible actions that can be processed or reprocessed at specified time.
Here the frequency is defined in the table, and an update of that frequency will require a restart of the service.

A scheduled job will be created for each active entry in that table running at a specified frequency.
In the case of user creation, it will retry to resubmit the request to the 3rd party service and mark the user as Active if successful and this request as successful, otherwise will increase the number of attempts and will be retried again next time the job kicks off again.
There is a maximum number of attempts.

# About the user registration via CSV
The ScheduledAction table also hold an entry for registering user reading a csv file.
The path to the csv file can be absolute or the file can be placed inside the resource folder of the application and is specified in the application properties file.
This gives us flexibility to update the location of the file without needed to restart the server (Will be possible with Spring Cloud Config).
The scheduled job will read the csv file and consume the same flow for creating a user via API (See above)

# Enhancement
- Create and return proper exception/error message when something wrong happens
- I would have put the scheduled action frequencies in a config-server, so that we could update it anytime without needing a restart of the service, but i didn't find a way to update the frequency of a schedule job during runtime.
- For users that failed to be created after many attempts, we can generate a report with details of users that failed to be registered
- Write Unit test (due to time constraint, I preferred to focus on functionality)
