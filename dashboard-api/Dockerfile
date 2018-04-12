FROM openjdk:8-jdk-slim
ADD ./target/dashboard-api-1.0.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/dashboard-api-1.0.0.jar"]