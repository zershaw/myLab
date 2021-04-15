SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `brands` related
-- ----------------------------

DROP TABLE IF EXISTS `T_BRAND_IMAGE`;
DROP TABLE IF EXISTS `T_BRAND`;
DROP TABLE IF EXISTS `T_BRAND_CATEGORY`;

CREATE TABLE `T_BRAND`
(
    `id`     int NOT NULL AUTO_INCREMENT,
    `name`   varchar(128) DEFAULT NULL,
    `webUrl` varchar(256) DEFAULT NULL,
    `imageName` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

