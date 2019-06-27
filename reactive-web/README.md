# Objective:

The objective of this project is to build reactive API's with Spring WebFlux. This project will use Embedded MongoDB and exposes the REST API's for all the CRUD operations with Reactive MongoDB.

# Pre-requsites:
Should be aware of Reactive programming concepts and Spring Reactor, which is explained in my personal blog.
https://shyamtechno.blogspot.com/2019/06/basics-of-reactive-programming-project.html

# Instructions for the setup:

* Git clone the project
* Import the project into any Java based IDE of your choice (like Eclipse or STS or IntelliJ etc)
* Once the project is imported, please review the Unit test cases available in the package  src\test\com\example\demo\ProfileServiceTest.java
to how all the Service methods are unit-tested.
* Execute the Unit Tests from IDE - i.e right click on the Unit test cases and run the Unit test cases
* You can also execute the test cases from command line. Open command line at the root folder level (\reactive-web) and run the following command:
mvn test