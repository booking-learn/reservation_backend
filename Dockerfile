FROM  eclipse-temurin:25-jre

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY healthcheck.sh /app/healthcheck.sh
RUN chmod +x /app/healthcheck.sh

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
