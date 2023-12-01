/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35-0ubuntu0.22.04.1)
 Source Host           : 42.192.15.218:3306
 Source Schema         : handshaker

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35-0ubuntu0.22.04.1)
 File Encoding         : 65001

 Date: 30/11/2023 17:01:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_storehouse
-- ----------------------------
DROP TABLE IF EXISTS `base_storehouse`;
CREATE TABLE `base_storehouse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `name` varchar(120) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `phone_no` varchar(30) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `facebook` varchar(120) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `contact_person` varchar(120) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for base_supplier
-- ----------------------------
DROP TABLE IF EXISTS `base_supplier`;
CREATE TABLE `base_supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `supplier_id` varchar(120) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `name` varchar(120) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `phone_no` varchar(30) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `contact_person` varchar(120) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `office_address` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `factory_address` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for custom_customer
-- ----------------------------
DROP TABLE IF EXISTS `custom_customer`;
CREATE TABLE `custom_customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `name` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `phone_no` varchar(60) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `birthdate` timestamp NULL DEFAULT NULL,
  `sex` varchar(30) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `member_id` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `member_level_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for custom_member_level
-- ----------------------------
DROP TABLE IF EXISTS `custom_member_level`;
CREATE TABLE `custom_member_level` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `name` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for order_order
-- ----------------------------
DROP TABLE IF EXISTS `order_order`;
CREATE TABLE `order_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `order_id` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `order_status` varchar(60) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `profit_sql_id` int DEFAULT NULL,
  `storehouse_sql_id` int DEFAULT NULL,
  `cashier_sql_id` int DEFAULT NULL,
  `member_sql_id` int DEFAULT NULL,
  `member_level_sql_id` int DEFAULT NULL,
  `discount` text COLLATE utf8mb4_bg_0900_as_cs,
  `payment_method` text COLLATE utf8mb4_bg_0900_as_cs,
  `ordered_product` text COLLATE utf8mb4_bg_0900_as_cs,
  `refunded_remarks` text COLLATE utf8mb4_bg_0900_as_cs,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for order_profit
-- ----------------------------
DROP TABLE IF EXISTS `order_profit`;
CREATE TABLE `order_profit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `order_sql_id` int DEFAULT NULL,
  `member_sql_id` int DEFAULT NULL,
  `cashier_sql_id` int DEFAULT NULL,
  `order_id` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `order_status` varchar(60) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `products_all_price` decimal(10,2) DEFAULT NULL,
  `products_all_profit` decimal(10,2) DEFAULT NULL,
  `order_all_discount` decimal(10,2) DEFAULT NULL,
  `origin_total_profit` decimal(10,2) DEFAULT NULL,
  `origin_total_price` decimal(10,2) DEFAULT NULL,
  `new_total_profit` decimal(10,2) DEFAULT NULL,
  `refund_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for order_refunded
-- ----------------------------
DROP TABLE IF EXISTS `order_refunded`;
CREATE TABLE `order_refunded` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `order_sql_id` int DEFAULT NULL,
  `profit_sql_id` int DEFAULT NULL,
  `storehouse_sql_id` int DEFAULT NULL,
  `refunded_remarks` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `refunded_price` decimal(10,2) DEFAULT NULL,
  `refunded_quantity` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `refunded_info` text COLLATE utf8mb4_bg_0900_as_cs,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for prod_broken
-- ----------------------------
DROP TABLE IF EXISTS `prod_broken`;
CREATE TABLE `prod_broken` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `date` timestamp NULL DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `storehouse_sql_id` int DEFAULT NULL,
  `product_sql_id` int DEFAULT NULL,
  `variation_sql_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for prod_label
-- ----------------------------
DROP TABLE IF EXISTS `prod_label`;
CREATE TABLE `prod_label` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `name` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `is_show` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for prod_product
-- ----------------------------
DROP TABLE IF EXISTS `prod_product`;
CREATE TABLE `prod_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `product_id` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `remarks` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `new_restock_date` timestamp NULL DEFAULT NULL,
  `new_restock_price` decimal(10,2) DEFAULT NULL,
  `new_selling_price` decimal(10,2) DEFAULT NULL,
  `new_lowest_price` decimal(10,2) DEFAULT NULL,
  `new_supplier_sql_id` int DEFAULT NULL,
  `new_supplier` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `labels` text COLLATE utf8mb4_bg_0900_as_cs,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for prod_product_and_label
-- ----------------------------
DROP TABLE IF EXISTS `prod_product_and_label`;
CREATE TABLE `prod_product_and_label` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `product_sql_id` int DEFAULT NULL,
  `label_sql_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for prod_restock_record
-- ----------------------------
DROP TABLE IF EXISTS `prod_restock_record`;
CREATE TABLE `prod_restock_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `product_sql_id` int DEFAULT NULL,
  `supplier_sql_id` int DEFAULT NULL,
  `restock_date` timestamp NULL DEFAULT NULL,
  `restock_price` decimal(10,2) DEFAULT NULL,
  `lowest_price` decimal(10,2) DEFAULT NULL,
  `selling_price` decimal(10,2) DEFAULT NULL,
  `restock_distribute` text COLLATE utf8mb4_bg_0900_as_cs,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for prod_variation
-- ----------------------------
DROP TABLE IF EXISTS `prod_variation`;
CREATE TABLE `prod_variation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `name` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for prod_variation_and_storehouse_and_product
-- ----------------------------
DROP TABLE IF EXISTS `prod_variation_and_storehouse_and_product`;
CREATE TABLE `prod_variation_and_storehouse_and_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `product_sql_id` int DEFAULT NULL,
  `variation_sql_id` int DEFAULT NULL,
  `storehouse_sql_id` int DEFAULT NULL,
  `quantity` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `version` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `username` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `phone_no` varchar(30) COLLATE utf8mb4_bg_0900_as_cs DEFAULT NULL,
  `storehouse_id` int DEFAULT NULL,
  `is_admin` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_as_cs;

SET FOREIGN_KEY_CHECKS = 1;
