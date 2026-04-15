FROM eclipse-temurin:21-jdk AS build

WORKDIR /build
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]