FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY . /app
WORKDIR /app
RUN chmod +x ./mvnw
RUN ./mvnw clean package
COPY target/linebot-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
