FROM openjdk
ADD target/account-service.jar account-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/account-service.jar"]
EXPOSE 2222