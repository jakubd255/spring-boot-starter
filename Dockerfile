FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/app.jar /app/app.jar
RUN mkdir -p /app/uploads
VOLUME /app/uploads
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]