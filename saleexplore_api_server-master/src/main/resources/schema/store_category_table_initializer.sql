SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store_brand`
-- ----------------------------
DROP TABLE IF EXISTS `T_STORE_CATEGORY`;

CREATE TABLE `T_STORE_CATEGORY`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `storeId`    bigint(20) NOT NULL,
    `categoryId` smallint   NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT FK_T_STORE_CATEGORY_category_Id FOREIGN KEY (categoryId) REFERENCES T_CATEGORY (id),
    CONSTRAINT FK_T_STORE_CATEGORY_store_Id FOREIGN KEY (storeId) REFERENCES T_STORE (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;