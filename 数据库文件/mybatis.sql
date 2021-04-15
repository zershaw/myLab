/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mybatis

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-04-15 12:37:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门表';

-- ----------------------------
-- Records of department
-- ----------------------------

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_name` varchar(100) DEFAULT NULL COMMENT '员工名',
  `dept_id` int(11) NOT NULL COMMENT '关联部门表主键',
  `gender` varchar(10) NOT NULL COMMENT '性别',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `phone` varchar(50) NOT NULL COMMENT '手机号',
  `hire_date` datetime DEFAULT NULL COMMENT '入职时间',
  PRIMARY KEY (`emp_id`) USING BTREE,
  KEY `dept_id` (`dept_id`) USING BTREE,
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='员工表';

-- ----------------------------
-- Records of employee
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `pwd` varchar(30) DEFAULT NULL,
  `perms` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '狂神', '123456', 'user:add');
INSERT INTO `user` VALUES ('2', '张三', 'abcdef', null);
INSERT INTO `user` VALUES ('3', '李四', '987654', null);
INSERT INTO `user` VALUES ('4', 'root', '123456', 'user:update');
