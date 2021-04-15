SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `premium discount`
-- ----------------------------
DROP TABLE IF EXISTS `T_ADS`;
CREATE TABLE `T_ADS`
(
    `id`           int       NOT NULL AUTO_INCREMENT,
    `redirectUrl`  VARCHAR(256),
    `imageName`    VARCHAR(256),
    `isActive`     TINYINT            default 1,
    `orderIndex`   INT                default 0,
    `redirectType` TINYINT            default 0,
    `displayType`  TINYINT            default 0,
    `createdTime`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;