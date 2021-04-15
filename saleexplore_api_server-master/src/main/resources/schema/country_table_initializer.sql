SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `country`
-- ----------------------------
DROP TABLE IF EXISTS `T_COUNTRY`;
CREATE TABLE `T_COUNTRY`
(
    `id`          smallint     NOT NULL AUTO_INCREMENT,
    `iso2`        varchar(15)  NOT NULL,
    `countryName` varchar(128) NOT NULL,
    `currency`    varchar(15)  NOT NULL,
    `phoneCode`   varchar(10)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

