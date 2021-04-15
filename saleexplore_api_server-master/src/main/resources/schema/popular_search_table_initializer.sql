SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store`
-- ----------------------------
DROP TABLE IF EXISTS `T_POPULAR_SEARCH`;

CREATE TABLE `T_POPULAR_SEARCH`
(
    `id`    int(11)      NOT NULL AUTO_INCREMENT,
    `name`  varchar(128) NOT NULL,
    `score` TINYINT(4)   NOT NULL DEFAULT 0,
    `searchType`  TINYINT(4)   NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY `index_score` (`score`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;