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

# INSERT INTO `USER` SET NAME = 'TEST1';