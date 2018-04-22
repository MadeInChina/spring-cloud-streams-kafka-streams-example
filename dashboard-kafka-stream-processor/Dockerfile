FROM openjdk:8-jdk-slim
MAINTAINER slam.han <slam.han@finnplay.com>

ADD ./target/streams.jar /app/
CMD ["java", "-Xmx1200m", "-jar", "/app/streams.jar"]