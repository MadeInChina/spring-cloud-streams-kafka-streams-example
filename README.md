How to build
 ````
 cd dashboard-api
     1.mvn clean install
     2.docker build -t docker.madeinchina.net/dashboard-api:latest .
 cd dashboard-kafka-stream-processor
     1.mvn clean install
     2.docker build -t docker.madeinchina.net/kafka-stream:latest .
How to run
  under root folder run
  docker-compose up
  
How to generate data to kafka
  login to mysql on docker:
     mysql -h127.0.0.1 -P3307 -uroot -p123456
  then
     mysql> use kafka-stream;
     mysql> INSERT INTO `USER` SET NAME = 'TEST1';

How to check logs:
 e.g
  Search kafka-stream-processor container
  docker ps | grep "docker.madeinchina.net/kafka-stream:latest" | awk '{ print $1 }'  
  4380aebe5328

  check logs for kafka-stream-processor
  docker logs -f  4380aebe5328
