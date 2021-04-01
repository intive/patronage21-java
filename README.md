# User's module
This is user's module that provides basic functionalities for user's data management \
All available endpoints are shown on Swagger
## Table of contents
* [Technologies](#technologies)
* [Prerequisites](#prerequisites)
* [Setup](#setup)
* [Usage](#usage)
* [Swagger](#swagger)
## Technologies
JDK LTS 11 
Gradle
## Prerequisites
* JDK 11 - download jdk 11 installation guide at ````https://docs.oracle.com/en/java/javase/11/install/````
* Gradle - download current release of gradle from ````https://gradle.org/install/````
* Git - download git ````https://git-scm.com/downloads````
## Setup
Clone this repo to your desktop, go to it's root directory and change branch using ````git checkout branch_name````
## Usage
After you clone this repo to your desktop go to it's root directory change current branch to dev
using ````git checkout dev```` or other desired branch. Run ````gradlew build```` to build the project.
To start the application type ````gradlew bootRun```` after that you will be able to access it at ````localhost:8080````
Once starter swagger becomes available at ````http://localhost:8080/swagger-ui.html````  
## Swagger
You can find swagger under following link ````http://localhost:8080/swagger-ui.html````
It will be reachable once you start the application \
version 3.0.3