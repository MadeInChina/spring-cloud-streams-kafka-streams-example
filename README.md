How to build
 cd dashboard-api
     1.mvn clean install
     2.docker build -t docker.madeinchina.net/dashboard-api:latest .
 cd dashboard-kafka-stream-processor
     1.mvn clean install
     2.docker build -t docker.madeinchina.net/kafka-stream:latest .
How to run
  under root folder run
  docker-compose up