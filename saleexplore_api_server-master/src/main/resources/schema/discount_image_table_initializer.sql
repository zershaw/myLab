SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `discount_products`
-- ----------------------------
DROP TABLE IF EXISTS `T_DISCOUNT_IMAGE`;
CREATE TABLE `T_DISCOUNT_IMAGE`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `discountId` bigint(20) NOT NULL,
    `orderIndex` int        NOT NULL,
    `title`      varchar(128) DEFAULT NULL,
    `imageName`  varchar(128) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT FK_Discount_IMAGE_Id FOREIGN KEY (discountId) REFERENCES T_DISCOUNT (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;