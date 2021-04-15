SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `T_CITY`;
CREATE TABLE `T_CITY`
(
    `id`             int          NOT NULL AUTO_INCREMENT,
    `cityName`       varchar(128) NOT NULL,
    `countryId`      smallint     NOT NULL,
    `countryName`    varchar(128) NOT NULL,
    `currency`       varchar(128) NOT NULL,
    `timezone`       varchar(128) NOT NULL,
    `cityFlightCode` varchar(128) NOT NULL,
    `isActive`       TINYINT        DEFAULT 0,
    `latitude`       DECIMAL(10, 8) DEFAULT 0,
    `longitude`      DECIMAL(10, 8) DEFAULT 0,
    PRIMARY KEY (`id`),
    CONSTRAINT FK_T_CITY_country_Id FOREIGN KEY (countryId) REFERENCES T_COUNTRY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

