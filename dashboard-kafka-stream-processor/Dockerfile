FROM openjdk:8-jdk-slim
ADD ./target/streams.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/streams.jar"]