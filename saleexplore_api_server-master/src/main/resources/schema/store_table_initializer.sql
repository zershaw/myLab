SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store`
-- ----------------------------
DROP TABLE IF EXISTS `T_STORE`;
CREATE TABLE `T_STORE`
(
    `id`                bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`              varchar(128) NOT NULL,
    `webUrl`            varchar(256),
    `location`          varchar(256),
    `latitude`          DECIMAL(10, 8)        DEFAULT NULL,
    `longitude`         DECIMAL(11, 8)        DEFAULT NULL,
    `geoPoint`          POINT        NOT NULL COMMENT 'geo point made from the lat and longi',
    `isVerified`        tinyint               DEFAULT 0,
    `isDeleted`         tinyint               DEFAULT 0,
    `popularity`        DECIMAL(3, 2)         DEFAULT 0,
    `phoneNumber`       varChar(32),
    `infoDescription`   TEXT                  DEFAULT NULL,
    `openingHours`      varChar(472),
    `email`             varChar(256),
    `cityId`            int          NOT NULL DEFAULT 0,
    `categoryId`        smallint     NOT NULL DEFAULT 0,
    `mallId`            bigint(20)   NOT NULL DEFAULT 0,
    `lastUpdatedTime`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `numberOfDiscounts` int                   DEFAULT 0,
    PRIMARY KEY (`id`),
    SPATIAL KEY `INDEX_GEO_POINT` (`geoPoint`),
    CONSTRAINT FK_T_STORE_mall_Id FOREIGN KEY (mallId) REFERENCES T_MALL (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='the table for the store data';

