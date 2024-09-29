FROM eclipse-temurin:21-jre-alpine
WORKDIR /usr/app
COPY ./target/azuredemo-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "azuredemo-0.0.1-SNAPSHOT.jar"]