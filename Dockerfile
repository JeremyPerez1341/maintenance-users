FROM openjdk:20-jdk

WORKDIR /app

COPY ./target/maintenance-users-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "maintenance-users-0.0.1-SNAPSHOT.jar"]