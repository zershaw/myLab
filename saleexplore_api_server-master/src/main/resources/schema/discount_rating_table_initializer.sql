SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store`
-- ----------------------------
DROP TABLE IF EXISTS `T_DISCOUNT_RATING`;
CREATE TABLE `T_DISCOUNT_RATING`
(
    `userId`      bigint(20) NOT NULL,
    `score`       float               default 0,
    `discountId`  bigint(20) NOT NULL,
    `createdTime` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`userId`, `discountId`),
    CONSTRAINT FK_T_DISCOUNT_RATING_user_Id FOREIGN KEY (userId) REFERENCES T_USER (id) ON DELETE CASCADE,
    CONSTRAINT FK_T_DISCOUNT_RATING_store_Id FOREIGN KEY (discountId) REFERENCES T_DISCOUNT (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

