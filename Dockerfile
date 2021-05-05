FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11:alpine
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*jar /app/spring-boot-application.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/spring-boot-application.jar" ]