# Patronative - Intive Patronage management app
This is a back-end module of the Patronative application which supports user management of the Intive Patronage project.
## Table of contents
* [Technologies](#technologies)
* [Features](#features)
* [Swagger](#swagger)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Setup](#setup)
* [Usage](#usage)
* [Status](#status)
## Technologies
* JDK 11 LTS
* Gradle
* Spring Boot 2.4.4
* Springdoc-openapi 1.5
* Lombok
## Features
The application supports following user management operations:
* listing users and user-related data (e.g. technological groups)
* searching for a user
* updating a user data
* saving a user
## Swagger
You can find swagger under following link http://localhost:8080/swagger-ui.html. \
It will be reachable once you start the application. 
## Prerequisites
To build and run this project, you will need:
* JDK 11 - installation guide -> https://docs.oracle.com/en/java/javase/11/install
* Gradle -> https://gradle.org/install
* Git -> https://git-scm.com/downloads
## Installation
1) Clone this repo to your desktop in Git Bash:
````
git clone https://github.com/intive/patronage21-java.git
````
2) Go to it's root directory:
````
cd patronage21-java
````
3) Change branch to the desired one (dev for testers):
````
git checkout branch_name
````
4) Build the project with Gradle.\
   Run command prompt, go to the project's root directory (patronage21-java) and type:
````
gradlew build
````
If the build went successful, you will see "BUILD SUCCESSFUL" after the project finishes building. Otherwise, report the problem to developers.
## Setup
To run this project, run command prompt, go to the project's root directory (patronage21-java), and type the following command:
````
gradlew bootRun
````
or:
````
cd build/libs
java -jar patronative-0.0.1-SNAPSHOT.jar
````
If running the project went successful, you will see "Started Patronative Application" as the last log line displayed in the command prompt.\
After that, you will be able to access it at http://localhost:8080.
## Usage
All available endpoints and their parameters can be found in Swagger: http://localhost:8080/swagger-ui.html
### Examples of use
The application must be started first.\
To test this app you can use Swagger or Postman.
#### Swagger
1) Open http://localhost:8080/swagger-ui.html in your web browser.
2) Open one of the controller's tab (e.g. user-controller).
3) Choose the desired operation (e.g. PUT /api/users).
4) In "Parameters" section, click "Try it out".
5) Set desired parameters and click "Execute".

You will see your request and a response from the API below.
#### Postman
You will need Postman (https://www.postman.com/downloads/).
1) Create a new Collection and name it.
2) Click your new Collection with the right mouse button and select "Add request".
3) Choose one of the REST operations and enter the request URL like in the example below:
* GET http://localhost:8080/api/groups
    - returns the list of technological groups (this function is not available yet)\
      Parameters: None
      
or:
* GET http://localhost:8080/api/users?firstName=Anna
    - search for a user (this function is not available yet)\
      Parameters: firstName, lastName, userName
4) Click "Send" and you will see a response from the API below.
## Status
This project is still under development.