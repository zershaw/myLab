SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `categories`
-- ----------------------------
DROP TABLE IF EXISTS `T_CATEGORY`;
CREATE TABLE `T_CATEGORY`
(
    `id`       smallint NOT NULL AUTO_INCREMENT,
    `parentId` smallint     DEFAULT 0,
    `name`     varchar(128) DEFAULT NULL,
    `synonyms` varchar(128) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

