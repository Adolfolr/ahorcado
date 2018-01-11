-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bbddahorcado
-- ------------------------------------------------------
-- Server version	5.6.38-log

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
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `idUsuarios` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Password` varchar(256) DEFAULT NULL,
  `Puntuacion` int(11) DEFAULT NULL,
  `PartidasG` int(11) DEFAULT NULL,
  `PartidasP` int(11) DEFAULT NULL,
  `Media` float NOT NULL DEFAULT '0',
  `idPalabra` int(11) DEFAULT NULL,
  `ListaAciertos` varchar(27) DEFAULT NULL,
  `ListaFallos` varchar(27) DEFAULT NULL,
  PRIMARY KEY (`idUsuarios`),
  KEY `idPalabra_idx` (`idPalabra`),
  CONSTRAINT `idPalabra` FOREIGN KEY (`idPalabra`) REFERENCES `palabras` (`idPalabra`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Rafa','12345',24,7,1,0.875,9,'',''),(2,'Clara','54321',19,4,2,0.666667,7,'','A'),(3,'Adolfo','aeiou',7,2,3,0.4,6,'AD',''),(4,'Manolo','1234',0,0,0,0,1,'',''),(5,'Jaime','1234',0,0,0,0,1,'',''),(6,'Juanan','patata',0,0,0,0,1,'',''),(7,'Alfredo','ui',0,0,0,0,1,'',''),(8,'Jose','1234',0,0,0,0,1,'',''),(9,'Emilio','12345',0,0,0,0,1,'',''),(10,'Juan Antonio','12345',0,0,0,0,1,'',''),(11,'David','12345',0,0,0,0,1,'',''),(12,'Miguel','pass',11,4,0,1,5,'',''),(13,'Luis','889',0,0,0,0,1,'',''),(14,'Dani','dani',0,0,0,0,1,'',''),(15,'Manuel','m',0,0,0,0,1,'',''),(16,'Prueb','1234',0,0,0,0,1,'',''),(17,'Rafa2','12345',0,0,0,0,1,'',''),(18,'Pepe','pepe',0,0,0,0,1,'',''),(19,'Mariano','123',0,0,0,0,1,'',''),(20,'Hola','123',0,0,0,0,1,'',''),(21,'Angel','1234',6,1,1,0.5,3,'',''),(22,'Jorge','jorge',6,1,1,0.5,3,'',''),(24,'Clara2','123',0,0,0,0,1,'',''),(25,'josete','1234',3,1,1,0.5,3,'',''),(26,'Pepito','1234',0,0,0,0,1,'',''),(27,'Lola','1234',0,0,0,0,1,'',''),(28,'Lolita','123456',10,3,1,0.75,5,'A','C'),(29,'Patata','123',6,1,0,1,2,'',''),(30,'Rosa','123',0,0,0,0,1,'',''),(31,'Manoloa','1',0,0,0,0,1,'F','AE');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-10 13:34:18
