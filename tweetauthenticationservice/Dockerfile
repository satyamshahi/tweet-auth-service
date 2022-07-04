FROM openjdk:11
ADD target/AuthenticationService.jar AuthenticationService.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar","AuthenticationService.jar"]