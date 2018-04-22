How to build
 cd dashboard-api
     1.mvn clean install
     2.docker build -t slamhan/dashboard-api:latest .
 cd dashboard-kafka-stream-processor
     1.mvn clean install
     2.docker build -t slamhan/kafka-stream:latest .
How to run
  under docker-compose.yml folder run
  docker-compose up -d
  
  
Ho to generate data

  1.mysql -h127.0.0.1 -P3307 -uroot -p123456

  2.USE `kafka-stream`;

  3.INSERT INTO `USER` SET NAME = 'TEST1';

  4.INSERT INTO `GAME_TRANSACTION` (PROMO_BET, PROMO_GGR, CASH_BET, CASH_GGR) VALUES (1.0, 2.0, 3.0, 4.0);


How to reproduce problem:
 1.visit http://localhost:8082/user/count/2018-04-22  or 
      http://localhost:8082/game/transaction/store/2018-04-22
 2.Then one of it should got exception: 
 from page:     
 Whitelabel Error Page
 This application has no configured error view, so you are seeing this as a fallback.
 
 Sun Apr 22 03:22:24 UTC 2018
 There was an unexpected error (type=Internal Server Error, status=500).
 the state store, gtmv, may have migrated to another instance.
 
 from logs:
 04-22 03:22:24 [reactor-http-nio-2] ERROR o.s.b.a.w.r.e.DefaultErrorWebExceptionHandler - Failed to handle request [GET http://localhost:8082/game/transaction/store/2018-04-22]
 org.apache.kafka.streams.errors.InvalidStateStoreException: the state store, gtmv, may have migrated to another instance.
 	at org.apache.kafka.streams.state.internals.QueryableStoreProvider.getStore(QueryableStoreProvider.java:60)
 	at org.apache.kafka.streams.KafkaStreams.store(KafkaStreams.java:1043)
 	at org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry.getQueryableStoreType(QueryableStoreRegistry.java:47)
 	at com.madeinchina.streams.api.GameTransactionStoreController.games(GameTransactionStoreController.java:34)
 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 	at sun.reflect.NativeMethodAccessor
  
  
load test refer to https://dzone.com/articles/raw-performance-numbers-spring-boot-2-webflux-vs-s