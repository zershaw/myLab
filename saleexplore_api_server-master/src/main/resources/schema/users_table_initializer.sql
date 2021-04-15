SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `email`                  varchar(256) UNIQUE DEFAULT NULL,
    `emailVerified`          TINYINT             DEFAULT 0,
    `username`               varchar(256)        DEFAULT NULL,
    `password`               varchar(256)        DEFAULT NULL,
    `imageName`              varchar(256)        DEFAULT NULL,
    `userGender`             TINYINT             DEFAULT 0,
    `preferLanguage`         TINYINT             DEFAULT 0,
    `dateOfBirth`            DATE,
    `isDeleted`              TINYINT             DEFAULT 0,
    `isEnabled`              TINYINT             DEFAULT 1,
    `isHunterEnabled`        TINYINT             DEFAULT 1,
    `isHunterPremium`        TINYINT             DEFAULT 0,
    `isEmailVerified`        TINYINT             DEFAULT 0,
    `isEmailMarketEnabled`   TINYINT             DEFAULT 1,
    `lastTimeLogin`          TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lastTimeActive`         TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `dateTimeSignUp`         TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `authProvider`           TINYINT             DEFAULT 0,
    `providerId`             varchar(256) UNIQUE DEFAULT NULL,
    `mobilePhoneNumber`      varchar(256)        DEFAULT NULL,
    `mobilePhoneCountryCode` int                 DEFAULT 0,
    `cityId`                 int                 DEFAULT 0,
    `osType`                 TINYINT             DEFAULT 0,
    `appVersionCode`         int(6)              DEFAULT 0,
    `deviceToken`            varchar(256)        DEFAULT NULL,
    `geoPosition`            POINT      NOT NULL,
    PRIMARY KEY (`id`),
    SPATIAL KEY `INDEX_USER_GEO_POINT` (`geoPosition`),
    KEY `INDEX_USER_LAST_ACTIVE_TIME` (`lastTimeActive`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



DROP TABLE IF EXISTS `T_USER_EXT`;
CREATE TABLE `T_USER_EXT`
(
    `id`                 bigint(20) NOT NULL,
    `deviceUUID`         varchar(256) DEFAULT NULL,
    `deviceModel`        varchar(32)  DEFAULT NULL,
    `deviceVersion`      varchar(256) DEFAULT NULL,
    `deviceManufacturer` varchar(32)  DEFAULT NULL,
    `deviceIsVirtual`    TINYINT      DEFAULT 0,

    PRIMARY KEY (`id`),
    CONSTRAINT FK_User_Ext_Id FOREIGN KEY (id) REFERENCES T_USER (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



DROP TABLE IF EXISTS `T_USER_INTERESTS`;
CREATE TABLE `T_USER_INTERESTS`
(
    `userId`      bigint(20) NOT NULL,
    `categoryId`  smallint   NOT NULL,
    `score`       int        NOT NULL,
    `createdTime` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`userId`, `categoryId`),
    CONSTRAINT FK_User_Interests_user_Id FOREIGN KEY (userId) REFERENCES T_USER (id) ON DELETE CASCADE,
    CONSTRAINT FK_User_Interests_cat_Id FOREIGN KEY (categoryId) REFERENCES T_CATEGORY (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
