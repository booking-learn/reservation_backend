FROM eclipse-temurin:25-jdk

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

RUN groupadd spring && useradd -r -g spring spring && \
    cp target/*.jar app.jar && \
    chown spring:spring app.jar

USER spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]