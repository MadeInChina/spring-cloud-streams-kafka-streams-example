#http://maxwells-daemon.io/quickstart/  Maxwell stores all the state it needs within the mysql server itself, in the database called specified by the schema_database option. By default the database is named maxwell.
GRANT ALL on maxwell.* to 'maxwell'@'%' identified by '@password';
GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'maxwell'@'%';


CREATE DATABASE IF NOT EXISTS `kafka-stream`;

USE `kafka-stream`;

CREATE TABLE IF NOT EXISTS `USER` (
  `PK_USER_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Auto generated primary key',
  `NAME` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`PK_USER_ID`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `GAME_TRANSACTION` (
  `PK_GAME_TRANSACTION_ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Auto generated primary key',
  `PROMO_BET` DOUBLE,
  `PROMO_GGR` DOUBLE,
  `CASH_BET` DOUBLE,
  `CASH_GGR` DOUBLE,
  PRIMARY KEY (`PK_GAME_TRANSACTION_ID`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `USER` SET NAME = 'TEST1';

INSERT INTO `GAME_TRANSACTION` (PROMO_BET, PROMO_GGR, CASH_BET, CASH_GGR) VALUES (1.0, 2.0, 3.0, 4.0);