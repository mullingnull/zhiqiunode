CREATE DATABASE  IF NOT EXISTS `zhiqiunode` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `zhiqiunode`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: zhiqiunode
-- ------------------------------------------------------
-- Server version	5.7.8-rc-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book_alter_history`
--

DROP TABLE IF EXISTS `book_alter_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_alter_history` (
  `alter_id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 NOT NULL,
  `summary` char(225) CHARACTER SET utf8 DEFAULT '无改动',
  `title` char(25) CHARACTER SET utf8 DEFAULT '无改动',
  `author` char(25) CHARACTER SET utf8 DEFAULT '无改动',
  `publisher` char(20) CHARACTER SET utf8 DEFAULT '无改动',
  `page` int(4) unsigned DEFAULT '0',
  `price` decimal(5,2) DEFAULT '0.00',
  `pub_date` date DEFAULT '2333-03-03',
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`alter_id`),
  UNIQUE KEY `alter_id_UNIQUE` (`alter_id`),
  KEY `user_id_idx` (`user_id`),
  FULLTEXT KEY `isbn13_idx` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍信息变动历史';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `book_info`
--

DROP TABLE IF EXISTS `book_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_info` (
  `book_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `isbn` char(13) CHARACTER SET utf8 NOT NULL,
  `title` char(25) CHARACTER SET utf8 DEFAULT '待编辑',
  `author` char(25) CHARACTER SET utf8 DEFAULT '待编辑',
  `publisher` char(20) CHARACTER SET utf8 DEFAULT '待编辑',
  `page` int(4) unsigned DEFAULT '0',
  `price` decimal(5,2) DEFAULT '0.00',
  `summary` char(225) CHARACTER SET utf8 DEFAULT '待编辑',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pub_date` date DEFAULT '2333-03-03',
  `updatable` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `book_id_UNIQUE` (`book_id`),
  UNIQUE KEY `isbn_UNIQUE` (`isbn`),
  UNIQUE KEY `UK1hmg5nj91r0x0xbjpp6ifa5hg` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍信息元数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `identify_code`
--

DROP TABLE IF EXISTS `identify_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `identify_code` (
  `code_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `vendor_id` int(8) NOT NULL,
  `identify_code` char(5) CHARACTER SET utf8 NOT NULL,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`code_id`),
  UNIQUE KEY `code_id_UNIQUE` (`code_id`),
  UNIQUE KEY `identify_code_UNIQUE` (`identify_code`),
  UNIQUE KEY `vendor_UNIQUE` (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户验证码缓存，时效两小时';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mine`
--

DROP TABLE IF EXISTS `mine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mine` (
  `mine_id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(8) unsigned NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 NOT NULL,
  `prefer` char(45) CHARACTER SET utf8 DEFAULT '',
  `qty` int(1) DEFAULT '1',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`mine_id`),
  UNIQUE KEY `mine_id_UNIQUE` (`mine_id`),
  UNIQUE KEY `isbn_userid_UNIQUE` (`isbn`,`user_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `time_idx` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户待捐赠图书信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vendor_id` int(8) NOT NULL,
  `vendee_id` int(8) NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 NOT NULL,
  `qty` int(1) DEFAULT '1',
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`),
  UNIQUE KEY `vendoreeisbn_UNIQUE` (`vendor_id`,`vendee_id`,`isbn`),
  KEY `vendee_idx` (`vendee_id`),
  FULLTEXT KEY `isbn_idx` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='捐赠达成生成的订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `nick_name` char(10) CHARACTER SET utf8 DEFAULT '',
  `gender` int(1) DEFAULT '0',
  `major` char(2) CHARACTER SET utf8 DEFAULT '',
  `am` char(45) CHARACTER SET utf8 DEFAULT '',
  `college` char(2) CHARACTER SET utf8 DEFAULT '',
  `enrol_year` year(4) DEFAULT '2012',
  `profile_id` int(8) DEFAULT '0',
  `wc_qrcode` char(20) CHARACTER SET utf8 DEFAULT '',
  `minor` char(2) CHARACTER SET utf8 DEFAULT '',
  `flag` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `usr_id_UNIQUE` (`user_id`),
  KEY `enrol_year_idx` (`enrol_year`),
  FULLTEXT KEY `major_idx` (`major`),
  FULLTEXT KEY `wcqrcode_idx` (`wc_qrcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `profile_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `email` char(20) CHARACTER SET utf8 DEFAULT '',
  `key_word` char(20) CHARACTER SET utf8 DEFAULT '',
  `qq_code` char(12) CHARACTER SET utf8 DEFAULT '',
  `phone` char(11) CHARACTER SET utf8 DEFAULT '',
  `wechat_id` char(20) CHARACTER SET utf8 DEFAULT '',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`profile_id`),
  UNIQUE KEY `profile_id_UNIQUE` (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户详细信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_user`
--

DROP TABLE IF EXISTS `wechat_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_user` (
  `open_id` char(28) CHARACTER SET utf8 NOT NULL,
  `user_id` int(8) DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`open_id`),
  UNIQUE KEY `openid_UNIQUE` (`open_id`),
  KEY `user_id_idx` (`user_id`),
  FULLTEXT KEY `openid_idx` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信关注用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wish`
--

DROP TABLE IF EXISTS `wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wish` (
  `wish_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(8) unsigned NOT NULL,
  `isbn` char(13) CHARACTER SET utf8 NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`wish_id`),
  UNIQUE KEY `wish_id_UNIQUE` (`wish_id`),
  UNIQUE KEY `isbn_userid_UNIQUE` (`isbn`,`user_id`),
  KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户心愿单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'zhiqiunode'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `e_del_id_code_count` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `e_del_id_code_count` ON SCHEDULE EVERY 5 MINUTE STARTS '2016-05-01 02:33:43' ON COMPLETION NOT PRESERVE ENABLE DO CALL p_del_idcode_count (120) */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'zhiqiunode'
--
/*!50003 DROP PROCEDURE IF EXISTS `p_del_idcode_count` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_del_idcode_count`(IN `date_inter` INT)
BEGIN
    DELETE FROM identify_code WHERE NOW() > (datetime + INTERVAL date_inter MINUTE);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-09 14:37:42
