# Objective:

The objective of this project is to build reactive API's with Spring WebFlux and use Reactive MongoDB library.

#Blog Tutorial:

Read the blog tutorial: https://shyamtechno.blogspot.com/2019/12/mini-twitter-application-using-spring.html

# Pre-requsites:
The following softwares are to be installed on your machine for this code to work:
* JDK 11 or Open JDK 11
* Maven

# Instructions for the setup:

* Git clone the project
```
git clone https://github.com/shyamnarayan2001/SpringBoot_Reactive_Projects.git
```
* Once the clone is complete, you will see the sub-folder /spring-boot-reactive-mongo-twitter under the parent folder /SpringBoot_Reactive_Projects
* Now open Windows Command prompt OR Bash and go to the sub-directory path (SpringBoot_Reactive_Projects/spring-boot-reactive-mongo-twitter) and run the following command, which will pull the MongoDB docker image and run the same
```
docker-compose up
```
* Open another Command prompt and go to the directory path (SpringBoot_Reactive_Projects/spring-boot-reactive-mongo-twitter) and run the following command, which will start the application
```
mvnw clean install && java -jar target/spring-boot-reactive-mongo-twitter-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -
```
mvn spring-boot:run
```
* The server will start at http://localhost:8080.

# Exploring the Rest APIs:

* The application defines following REST APIs
```
1. GET /tweets - Get All Tweets

2. POST /tweets - Create a new Tweet

3. GET /tweets/{id} - Retrieve a Tweet by Id

3. PUT /tweets/{id} - Update a Tweet

4. DELETE /tweets/{id} - Delete a Tweet

5. GET /stream/tweets - Stream tweets to a browser as Server-Sent Events
``` 
# Running integration tests:

* The project also contains integration tests for all the Rest APIs. For running the integration tests, go to the root directory path (SpringBoot_Reactive_Projects/spring-boot-reactive-mongo-twitter) of the project and type the following in your terminal.
``` 
mvn test
``` 