SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `discount_products`
-- ----------------------------
DROP TABLE IF EXISTS `T_STORE_IMAGE`;
CREATE TABLE `T_STORE_IMAGE`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `storeId`    bigint(20) NOT NULL,
    `orderIndex` int        NOT NULL default 0,
    `title`      varchar(128)        DEFAULT NULL,
    `imageName`  varchar(128)        DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT FK_Store_IMAGE_Id FOREIGN KEY (storeId) REFERENCES T_STORE (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;