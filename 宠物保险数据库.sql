create database animalsec;
use animalsec;

CREATE TABLE `vendors` (
  `vendor_id` int NOT NULL AUTO_INCREMENT COMMENT '商家ID',
  `vendor_name` varchar(100) NOT NULL COMMENT '商家名称',
  `contact_email` varchar(100) NOT NULL COMMENT '联系邮箱',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT NULL COMMENT '商家地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家表';

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码（需加密存储）',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

CREATE TABLE `policy` (
  `policy_id` int NOT NULL AUTO_INCREMENT COMMENT '保险产品ID',
  `policy_name` varchar(100) NOT NULL COMMENT '保险产品名称',
  `description` text COMMENT '保险产品描述',
  `premium` decimal(10,2) NOT NULL COMMENT '保费',
  `coverage` decimal(10,2) NOT NULL COMMENT '保险覆盖金额',
  `term_months` int NOT NULL COMMENT '保险期限（月）',
  PRIMARY KEY (`policy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='保险产品表';

CREATE TABLE `pets` (
  `pet_id` int NOT NULL AUTO_INCREMENT COMMENT '宠物ID',
  `user_id` int NOT NULL COMMENT '用户ID，外键关联用户表',
  `pet_name` varchar(50) NOT NULL COMMENT '宠物名字',
  `species` varchar(50) NOT NULL COMMENT '宠物种类（如狗、猫）',
  `breed` varchar(50) DEFAULT NULL COMMENT '宠物品种',
  `age` int DEFAULT NULL COMMENT '宠物年龄',
  `gender` enum('Male','Female') DEFAULT NULL COMMENT '宠物性别',
  PRIMARY KEY (`pet_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `pets_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宠物表';

CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `pet_id` int NOT NULL COMMENT '宠物ID，外键关联宠物表',
  `policy_id` int NOT NULL COMMENT '保险产品ID',
  `order_status` enum('Pending','Active','Expired','Cancelled') NOT NULL COMMENT '订单状态',
  `total_price` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `start_date` date NOT NULL COMMENT '保险开始日期',
  `end_date` date NOT NULL COMMENT '保险结束日期',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `vendor_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `pet_id` (`pet_id`),
  KEY `vendor_id` (`vendor_id`),
  KEY `user_id` (`user_id`),
  KEY `policy_id` (`policy_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`pet_id`) ON DELETE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`vendor_id`) REFERENCES `vendors` (`vendor_id`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`policy_id`) REFERENCES `policy` (`policy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表'