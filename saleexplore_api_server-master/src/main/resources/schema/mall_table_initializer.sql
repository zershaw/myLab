SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store`
-- ----------------------------
DROP TABLE IF EXISTS `T_MALL`;
CREATE TABLE `T_MALL`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`         varchar(128) NOT NULL,
    `webUrl`       varchar(256)   DEFAULT NULL,
    `geoAddress`   varChar(256)   DEFAULT NULL COMMENT 'address in text format',
    `geoPoint`     POINT        NOT NULL COMMENT 'geo point made from the lat and longi',
    `phoneNumber`  varChar(32)    DEFAULT NULL,
    `latitude`     DECIMAL(10, 8) DEFAULT NULL,
    `longitude`    DECIMAL(11, 8) DEFAULT NULL,
    `openingHours` varChar(472)   DEFAULT NULL COMMENT 'the opening hours in encoded format',
    `isOnline`     TINYINT        DEFAULT 0 COMMENT 'whether this mall is a online one',
    `email`        varChar(256)   DEFAULT NULL,
    `cityId`       int            DEFAULT 0,
    PRIMARY KEY (`id`),
    SPATIAL KEY `INDEX_GEO_POINT` (`geoPoint`),
    CONSTRAINT FK_T_MALL_city_Id FOREIGN KEY (cityId) REFERENCES T_CITY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='the table for the malls data';

