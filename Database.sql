-- MySQL dump 10.13  Distrib 8.0.43, for macos15 (arm64)
--
-- Host: localhost    Database: main
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boarding_pass`
--

DROP TABLE IF EXISTS `boarding_pass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boarding_pass` (
  `BPN` bigint NOT NULL,
  `origin_airport` varchar(4) NOT NULL,
  `dest_airport` varchar(4) NOT NULL,
  `psg_name` varchar(255) NOT NULL,
  `flt_nr` varchar(255) NOT NULL,
  PRIMARY KEY (`BPN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boarding_pass`
--

LOCK TABLES `boarding_pass` WRITE;
/*!40000 ALTER TABLE `boarding_pass` DISABLE KEYS */;
/*!40000 ALTER TABLE `boarding_pass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `headphones`
--

DROP TABLE IF EXISTS `headphones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `headphones` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `status` int NOT NULL,
  `location` varchar(4) DEFAULT NULL,
  `battery` int NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `headphones`
--

LOCK TABLES `headphones` WRITE;
/*!40000 ALTER TABLE `headphones` DISABLE KEYS */;
INSERT INTO `headphones` VALUES (1,1,'EKBI',100);
/*!40000 ALTER TABLE `headphones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kiosk`
--

DROP TABLE IF EXISTS `kiosk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kiosk` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `airport` varchar(4) NOT NULL,
  `numOfAvailableHP` int NOT NULL,
  `airport_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kiosk`
--

LOCK TABLES `kiosk` WRITE;
/*!40000 ALTER TABLE `kiosk` DISABLE KEYS */;
INSERT INTO `kiosk` VALUES (1,'EKCH',7,'Copenhagen Kastrup'),(2,'EKBI',9,'Billund'),(3,'EKAH',10,'Aarhus'),(4,'ENGM',10,'Oslo Gardermoen'),(5,'ENBR',10,'Bergen Flesland'),(6,'ENVA',10,'Trondheim Værnes'),(7,'EKYT',10,'Aalborg'),(8,'ENTC',10,'Tromsø'),(9,'ESGG',10,'Goteborg Landvetter'),(10,'ESSA',10,'Stockholm Arlanda'),(11,'ESMS',10,'Malmo '),(12,'ESSB',10,'Stockholm Bromma');
/*!40000 ALTER TABLE `kiosk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `BPN` bigint NOT NULL,
  `headphonesID` int DEFAULT NULL,
  `originKioskID` int NOT NULL,
  `destKioskID` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`BPN`),
  KEY `headphonesID` (`headphonesID`),
  KEY `originKioskID` (`originKioskID`),
  KEY `destKioskID` (`destKioskID`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`BPN`) REFERENCES `boarding_pass` (`BPN`),
  CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`headphonesID`) REFERENCES `headphones` (`ID`),
  CONSTRAINT `transactions_ibfk_3` FOREIGN KEY (`originKioskID`) REFERENCES `kiosk` (`ID`),
  CONSTRAINT `transactions_ibfk_4` FOREIGN KEY (`destKioskID`) REFERENCES `kiosk` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-04 13:13:37
