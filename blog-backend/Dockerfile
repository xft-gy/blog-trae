FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY blog-common/pom.xml blog-common/
COPY blog-domain/pom.xml blog-domain/
COPY blog-application/pom.xml blog-application/
COPY blog-infrastructure/pom.xml blog-infrastructure/
COPY blog-interfaces/pom.xml blog-interfaces/

RUN mvn dependency:go-offline -B

COPY blog-common/src blog-common/src
COPY blog-domain/src blog-domain/src
COPY blog-application/src blog-application/src
COPY blog-infrastructure/src blog-infrastructure/src
COPY blog-interfaces/src blog-interfaces/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/blog-interfaces/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
