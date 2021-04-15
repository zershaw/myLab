SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store`
-- ----------------------------
DROP TABLE IF EXISTS `T_EMAIL_PWD_VERI`;
CREATE TABLE `T_EMAIL_PWD_VERI`
(
    `email`       varchar(128) NOT NULL,
    `veriCode`    char(5)      NOT NULL,
    `createdTime` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

