-- Создание базы данных
create database if not exists carshop default charset utf8;

-- Определение базы данных по умолчанию
use carshop;

-- Создание таблиц
CREATE TABLE IF NOT EXISTS `owner`
(
 `ownerId`  integer NOT NULL AUTO_INCREMENT ,
 `name`     varchar(45) NOT NULL ,
 `lastname` varchar(45) NOT NULL ,
 `email`    varchar(100) NULL ,
 `phone`    varchar(15) NOT NULL ,
 `birthday` date NULL ,

PRIMARY KEY (`ownerId`)
);

CREATE TABLE IF NOT EXISTS `car`
(
 `carId`   integer NOT NULL AUTO_INCREMENT ,
 `brand`   varchar(45) NOT NULL ,
 `model`   varchar(45) NULL ,
 `color`   varchar(45) NOT NULL ,
 `year`    date NOT NULL ,
 `mileage` integer NULL ,
 `owner`   integer NOT NULL ,
 `price`   decimal NOT NULL ,

PRIMARY KEY (`carId`),
KEY `fkIdx_21` (`owner`),
CONSTRAINT `FK_21` FOREIGN KEY `fkIdx_21` (`owner`) REFERENCES `owner` (`ownerId`)
);






