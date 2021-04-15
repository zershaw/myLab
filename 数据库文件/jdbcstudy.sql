/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : jdbcstudy

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-04-15 12:37:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(40) DEFAULT NULL,
  `PASSWORD` varchar(40) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '詹韩峰', '123456', 'zs@sina.com', '1980-12-04');
INSERT INTO `users` VALUES ('2', 'lisi', '123456', 'lisi@sina.com', '1981-12-04');
INSERT INTO `users` VALUES ('3', 'wangwu', '123456', 'wangwu@sina.com', '1979-12-04');
INSERT INTO `users` VALUES ('4', 'kuangshen', '123456', '836613736@qq.com', '2011-01-01');
INSERT INTO `users` VALUES ('5', '陈泽潇', '123456', '836613736@qq.com', '2011-01-01');
INSERT INTO `users` VALUES ('6', 'hfq', '123456', '836613736@qq.com', '2011-01-01');
INSERT INTO `users` VALUES ('7', '更新', 'zzzzzzzzz', '2333@qq.com', '2020-11-04');
INSERT INTO `users` VALUES ('10', '小明', '1234567', null, null);
INSERT INTO `users` VALUES ('11', '小猪', '1234567', null, null);
INSERT INTO `users` VALUES ('12', '小猪', '1234567', null, null);
INSERT INTO `users` VALUES ('13', '小猪', '1234567', null, null);
