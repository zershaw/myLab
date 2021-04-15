DROP TABLE IF EXISTS `T_USER_SEARCH_HISTORY`;
CREATE TABLE `T_USER_SEARCH_HISTORY`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `userId`      bigint(20) NOT NULL,
    `kwQuery`     varchar(256)        DEFAULT NULL,
    `createdTime` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `searchType`  tinyint             DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX index_kwQuery (`kwQuery`) USING BTREE,
    CONSTRAINT FK_User_Search_history_Id FOREIGN KEY (userId) REFERENCES T_USER (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
