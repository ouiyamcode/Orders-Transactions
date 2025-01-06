-- MySQL dump 10.13  Distrib 8.0.40, for Linux (x86_64)
--
-- Host: localhost    Database: OrdersTransactions
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
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Orders` (
  `idOrder` int NOT NULL AUTO_INCREMENT,
  `dateCreation` varchar(255) DEFAULT NULL,
  `dateUpdate` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `totalAmount` float DEFAULT NULL,
  `idItem` int DEFAULT NULL,
  `itemType` varchar(50) DEFAULT NULL,
  `idUser` int DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`idOrder`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (1,'2025-01-01','2025-01-02','PENDING',100.5,1,'PRODUCT',1,'First order description'),(2,'2025-01-03','2025-01-04','FINISHED',250,2,'APPARTMENT',2,'Second order description'),(3,'2025-01-05','2025-01-06','CANCELLED',75.75,3,'PRODUCT',3,'Third order description');
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transactions`
--

DROP TABLE IF EXISTS `Transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Transactions` (
  `idTransaction` int NOT NULL AUTO_INCREMENT,
  `amount` float DEFAULT NULL,
  `paymentMethod` varchar(50) DEFAULT NULL,
  `idOrder` int DEFAULT NULL,
  `transactionStatus` varchar(50) DEFAULT NULL,
  `dateCreation` varchar(255) DEFAULT NULL,
  `dateUpdate` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`idTransaction`),
  KEY `idOrder` (`idOrder`),
  CONSTRAINT `Transactions_ibfk_1` FOREIGN KEY (`idOrder`) REFERENCES `Orders` (`idOrder`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transactions`
--

LOCK TABLES `Transactions` WRITE;
/*!40000 ALTER TABLE `Transactions` DISABLE KEYS */;
INSERT INTO `Transactions` VALUES (1,100.5,'CARD',1,'SUCCESS','2025-01-01','2025-01-02','Transaction for the first order'),(2,250,'PAYPAL',2,'FAILED','2025-01-03','2025-01-04','Transaction for the second order'),(3,75.75,'CASH',3,'PENDING','2025-01-05','2025-01-06','Transaction for the third order');
/*!40000 ALTER TABLE `Transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-06 15:24:12
