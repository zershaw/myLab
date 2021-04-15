SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store_brand`
-- ----------------------------
DROP TABLE IF EXISTS `T_STORE_BRAND`;

CREATE TABLE `T_STORE_BRAND`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `storeId` bigint(20) NOT NULL,
    `brandId` int        NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT FK_T_STORE_BRAND_brand_Id FOREIGN KEY (brandId) REFERENCES T_BRAND (id),
    CONSTRAINT FK_T_STORE_BRAND_store_Id FOREIGN KEY (storeId) REFERENCES T_STORE (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;