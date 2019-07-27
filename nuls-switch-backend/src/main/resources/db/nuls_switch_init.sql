/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : nuls_switch

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-15 22:27:21
*/


-- ----------------------------
-- create database
-- ----------------------------
-- CREATE DATABASE IF NOT EXISTS nuls_switch default charset utf8 COLLATE utf8_general_ci;

USE nuls_switch;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tx_order
-- ----------------------------
DROP TABLE IF EXISTS `tx_order`;
CREATE TABLE `tx_order` (
  `order_id` varchar(35) NOT NULL COMMENT '交易单号',
  `address` varchar(255) DEFAULT NULL COMMENT '挂单账户地址',
  `tx_type` tinyint(4) NOT NULL COMMENT '交易类型：1-买入、2-卖出',
  `from_token_id` int(11) NOT NULL COMMENT '原token',
  `to_token_id` int(11) NOT NULL COMMENT '目标token',
  `price` decimal(20,8) NOT NULL COMMENT '价格',
  `total_num` int(11) NOT NULL COMMENT '挂单总数量',
  `tx_num` int(11) DEFAULT NULL COMMENT '已完成交易数量',
  `total_amount` decimal(20,8) NOT NULL COMMENT '挂单总金额',
  `status` tinyint(4) NOT NULL COMMENT '状态：0-未交易、1-部分交易、2-完成交易、9-撤销',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户交易委托表：包括当前委托、历史委托';

-- ----------------------------
-- Table structure for tx_token
-- ----------------------------
DROP TABLE IF EXISTS `tx_token`;
CREATE TABLE `tx_token` (
  `token_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代币唯一标识',
  `token_symbol` varchar(30) NOT NULL COMMENT '代币符号,例如:NULS',
  `token_name` varchar(30) NOT NULL COMMENT '代币中文名称,例如:纳世币',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支持交易的代币';

-- ----------------------------
-- Table structure for tx_token_pair
-- ----------------------------
DROP TABLE IF EXISTS `tx_token_pair`;
CREATE TABLE `tx_token_pair` (
  `pair_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交易对ID',
  `from_token_id` int(11) NOT NULL COMMENT '原token',
  `to_token_id` int(11) NOT NULL COMMENT '转换为目标token',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pair_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代币兑换交易对';

-- ----------------------------
-- Table structure for tx_trade
-- ----------------------------
DROP TABLE IF EXISTS `tx_trade`;
CREATE TABLE `tx_trade` (
  `tx_id` varchar(255) NOT NULL COMMENT '交易流水号',
  `order_id` varchar(32) NOT NULL COMMENT '委托挂单ID',
  `address` varchar(255) DEFAULT NULL COMMENT '交易用户地址',
  `tx_num` int(11) DEFAULT NULL COMMENT '交易数量',
  `tx_hash` varchar (255) DEFAULT NULL COMMENT '交易发送到区块链后的Hash值',
  `status` tinyint(4) NOT NULL COMMENT '状态：0-未交易、1-完成交易、9-撤销',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='挂单交易明细，一个挂单可以对应多个明细记录';

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(512) NOT NULL COMMENT '用户登录token',
  `address` varchar(50) NOT NULL COMMENT '钱包地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_token` (`token`) USING HASH COMMENT 'Token索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证表：存储用户登录token与钱包地址关系';
