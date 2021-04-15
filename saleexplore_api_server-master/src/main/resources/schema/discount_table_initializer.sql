SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `discount`
-- the isOnline flag should be consistant with its belonged store. we have it in the discount table for query efficiency
-- ----------------------------

DROP TABLE IF EXISTS `T_DISCOUNT`;
CREATE TABLE `T_DISCOUNT`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `startTime`       TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `endTime`         TIMESTAMP  NULL,
    `publishTime`     TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lastUpdatedTime` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `title`           varchar(128)        DEFAULT NULL,
    `viewCount`       int                 DEFAULT 0,
    `likeCount`       int                 DEFAULT 0,
    `dislikeCount`    int                 DEFAULT 0,
    `infoDescription` TEXT                DEFAULT NULL,
    `storeId`         bigint(20) NOT NULL DEFAULT 0,
    `creatorId`       bigint(20) NOT NULL DEFAULT 0,
    `creatorRole`     smallint            DEFAULT 0,
    `categoryId`      int        NOT NULL DEFAULT 0,
    `relatedLinks`    varchar(256)        DEFAULT NULL,
    `videoName`       varchar(128)        DEFAULT NULL,
    `isOnline`        TINYINT             DEFAULT 0,
    `isValid`         TINYINT             DEFAULT 0,
    `isDeleted`       TINYINT             DEFAULT 0,
    `originalPrice`   float               default 0,
    `finalPrice`      float               default 0,
    `savingAmount`    float               default 0,
    `discountType`    TINYINT             DEFAULT 0,
    `discountTag`     TINYTEXT            DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT FK_T_DISCOUNT_store_Id FOREIGN KEY (storeId) REFERENCES T_STORE (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
