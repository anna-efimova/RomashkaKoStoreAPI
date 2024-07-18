FROM --platform=linux/amd64 openjdk:17-jdk-alpine

COPY target/RomashkaStoreAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
