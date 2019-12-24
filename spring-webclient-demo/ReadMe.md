# Objective:

The objective of this project is to understand the usage of Spring 5's WebClient and WebTestClient API's to consume the GitHub API's and to perform CRUD operations on user’s Github Repositories.

# Blog Tutorial:

Read the blog tutorial: https://shyamtechno.blogspot.com/2019/12/spring-5-webclient-and-webtestclient.html

# Pre-requsites:
The following softwares are to be installed on your machine for this code to work:
* JDK 11 or Open JDK 11
* Maven

# Instructions for the setup:

* Git clone the project
```
git clone https://github.com/shyamnarayan2001/SpringBoot_Reactive_Projects.git
```
* Once the clone is complete, you will see the sub-folder `/spring-webclient-demo` under the parent folder `/SpringBoot_Reactive_Projects`
* The source for this project can be found in the path `SpringBoot_Reactive_Projects/spring-webclient-demo`
* Create a personal access token on Github - `https://github.com/settings/tokens`
* Open `src/main/resources/application.properties` and specify your github username in `app.github.username` property, and your personal access token in `app.github.token` property.
* Now open Windows Command prompt OR Bash and go to the sub-directory path (`SpringBoot_Reactive_Projects/spring-webclient-demo`) and run the following command, which will start the application
```
mvnw clean install && java -jar target/spring-webclient-demo-0.0.1-SNAPSHOT
```

Alternatively, you can run the app without packaging it using -
```
mvn spring-boot:run
```
* The server will start at `http://localhost:8080`.

# Exploring the Rest APIs:

* The application defines following REST APIs
```
1. GET /repos - Get all the Github Repositories

2. POST /repos - Create a new Github Repository

3. GET /repos/{repo} - Retrieve a Repository by providing repository name

4. PATCH /repos/{repo} - Update a Repository

5. DELETE /repos/{repo} - Delete a Repository

``` 
# Running integration tests:

* The project also contains integration tests for all the Rest APIs. For running the integration tests, go to the root directory path (SpringBoot_Reactive_Projects/spring-webclient-demo) of the project and type the following in your terminal.
``` 
mvn test
``` 