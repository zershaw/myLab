SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `store`
-- ----------------------------
DROP TABLE IF EXISTS `T_STORE_RATING`;
CREATE TABLE `T_STORE_RATING`
(
    `userId`      bigint(20) NOT NULL,
    `score`       float               default 0,
    `storeId`     bigint(20) NOT NULL,
    `createdTime` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`userId`, `storeId`),
    CONSTRAINT FK_T_STORE_RATING_user_Id FOREIGN KEY (userId) REFERENCES T_USER (id) ON DELETE CASCADE,
    CONSTRAINT FK_T_STORE_RATING_store_Id FOREIGN KEY (storeId) REFERENCES T_STORE (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

