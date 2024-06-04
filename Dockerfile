FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY . /app
WORKDIR /app
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
