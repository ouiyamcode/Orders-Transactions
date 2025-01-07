-- MySQL dump 10.13  Distrib 8.0.40, for Linux (x86_64)
--
-- Host: localhost    Database: order_transaction
-- ------------------------------------------------------
-- Server version	8.0.40-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id_order` int NOT NULL AUTO_INCREMENT,
  `uuid_order` varchar(255) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `total_amount` float DEFAULT NULL,
  `id_item` int DEFAULT NULL,
  `uuid_item` varchar(255) DEFAULT NULL,
  `item_type` varchar(50) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  `uuid_user` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'ef4f7b2e-cce3-11ef-b916-b48c9d6df3cd','2025-01-01 00:00:00','2025-01-02 00:00:00','PENDING',100.5,1,'4b357396-cce4-11ef-b916-b48c9d6df3cd','PRODUCT',1,'45e2ef5e-cce4-11ef-b916-b48c9d6df3cd','First order description'),(2,'ef4f8184-cce3-11ef-b916-b48c9d6df3cd','2025-01-03 00:00:00','2025-01-04 00:00:00','FINISHED',250,2,'4b3579db-cce4-11ef-b916-b48c9d6df3cd','APPARTMENT',2,'45e2f5ae-cce4-11ef-b916-b48c9d6df3cd','Second order description'),(3,'ef4f8326-cce3-11ef-b916-b48c9d6df3cd','2025-01-05 00:00:00','2025-01-06 00:00:00','CANCELLED',75.75,3,'4b357b93-cce4-11ef-b916-b48c9d6df3cd','PRODUCT',3,'45e2f864-cce4-11ef-b916-b48c9d6df3cd','Third order description');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id_transaction` int NOT NULL AUTO_INCREMENT,
  `uuid_transaction` varchar(255) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `id_order` int DEFAULT NULL,
  `transaction_status` varchar(50) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id_transaction`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,'f1714d93-cce3-11ef-b916-b48c9d6df3cd',100.5,'CARD',1,'SUCCESS','2025-01-01 00:00:00','2025-01-02 00:00:00','Transaction for the first order'),(2,'f17152ea-cce3-11ef-b916-b48c9d6df3cd',250,'PAYPAL',2,'FAILED','2025-01-03 00:00:00','2025-01-04 00:00:00','Transaction for the second order'),(3,'f1715454-cce3-11ef-b916-b48c9d6df3cd',75.75,'CASH',3,'PENDING','2025-01-05 00:00:00','2025-01-06 00:00:00','Transaction for the third order');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-07 11:57:25
