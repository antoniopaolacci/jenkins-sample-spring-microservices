FROM openjdk
ADD target/gateway-service.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/gateway-service.jar"]
EXPOSE 8765