/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.27-log : Database - db_studentinfo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_studentinfo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_studentinfo`;

/*Table structure for table `t_class` */

DROP TABLE IF EXISTS `t_class`;

CREATE TABLE `t_class` (
  `classId` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(20) DEFAULT NULL,
  `gradeId` int(11) DEFAULT NULL,
  `classDesc` text,
  PRIMARY KEY (`classId`),
  KEY `FK_t_class` (`gradeId`),
  CONSTRAINT `FK_t_class` FOREIGN KEY (`gradeId`) REFERENCES `t_grade` (`gradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `t_class` */

insert  into `t_class`(`classId`,`className`,`gradeId`,`classDesc`) values (9,'1612001',4,'计算机软件培优班'),(10,'1612101',4,'计算机科学与技术专业1班'),(11,'1612201',4,'信息安全1班'),(12,'1612301',4,'软件工程专业1班');

/*Table structure for table `t_datadic` */

DROP TABLE IF EXISTS `t_datadic`;

CREATE TABLE `t_datadic` (
  `ddId` int(11) NOT NULL AUTO_INCREMENT,
  `ddTypeId` int(11) DEFAULT NULL,
  `ddValue` varchar(20) DEFAULT NULL,
  `ddDesc` text,
  PRIMARY KEY (`ddId`),
  KEY `FK_t_datadic` (`ddTypeId`),
  CONSTRAINT `FK_t_datadic` FOREIGN KEY (`ddTypeId`) REFERENCES `t_datadictype` (`ddTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8;

/*Data for the table `t_datadic` */

insert  into `t_datadic`(`ddId`,`ddTypeId`,`ddValue`,`ddDesc`) values (1,1,'男','1'),(2,1,'女','0'),(3,2,'中共党员',NULL),(4,2,'中共预备党员',NULL),(5,2,'共青团员',NULL),(6,2,'民革党员',NULL),(7,2,'民盟盟员',NULL),(8,2,'民建会员',NULL),(9,2,'民进会员',NULL),(10,2,'农工党党员',NULL),(11,2,'致公党党员',NULL),(12,2,'九三学社社员',NULL),(13,2,'台盟盟员',NULL),(14,2,'无党派人士',NULL),(15,2,'普通公民',NULL),(16,2,'港澳同胞',NULL),(334,3,'汉族',NULL),(335,3,'蒙古族',NULL),(336,3,'回族',NULL),(337,3,'藏族',NULL),(338,3,'维吾尔族',NULL),(340,3,'外国血统',NULL);

/*Table structure for table `t_datadictype` */

DROP TABLE IF EXISTS `t_datadictype`;

CREATE TABLE `t_datadictype` (
  `ddTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `ddTypeName` varchar(20) DEFAULT NULL,
  `ddTypeDesc` text,
  PRIMARY KEY (`ddTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_datadictype` */

insert  into `t_datadictype`(`ddTypeId`,`ddTypeName`,`ddTypeDesc`) values (1,'性别','男/女'),(2,'政治面貌',NULL),(3,'名族','');

/*Table structure for table `t_grade` */

DROP TABLE IF EXISTS `t_grade`;

CREATE TABLE `t_grade` (
  `gradeId` int(11) NOT NULL AUTO_INCREMENT,
  `gradeName` varchar(20) DEFAULT NULL,
  `gradeDesc` text,
  PRIMARY KEY (`gradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `t_grade` */

insert  into `t_grade`(`gradeId`,`gradeName`,`gradeDesc`) values (4,'2012级',''),(5,'2013级',''),(6,'2015级','');

/*Table structure for table `t_student` */

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `studentId` varchar(40) NOT NULL,
  `stuNo` varchar(20) DEFAULT NULL,
  `stuName` varchar(20) DEFAULT NULL,
  `stuSex` varchar(10) DEFAULT NULL,
  `stuBirthday` date DEFAULT NULL,
  `stuRxsj` date DEFAULT NULL,
  `stuNation` varchar(20) DEFAULT NULL,
  `stuZzmm` varchar(20) DEFAULT NULL,
  `classId` int(11) DEFAULT NULL,
  `stuDesc` text,
  `stuPic` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`studentId`),
  KEY `FK_t_student` (`classId`),
  CONSTRAINT `FK_t_student` FOREIGN KEY (`classId`) REFERENCES `t_class` (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_student` */

insert  into `t_student`(`studentId`,`stuNo`,`stuName`,`stuSex`,`stuBirthday`,`stuRxsj`,`stuNation`,`stuZzmm`,`classId`,`stuDesc`,`stuPic`) values ('8a91b64e-085a-4411-97c2-bab5e1ae4a2e','161200101','161200101','女','2015-12-02','2012-09-07','汉族','中共党员',9,'测试1',''),('b2d5933c-b4b5-4ba0-99a1-72d75f38fddc','161231010','张三','男','2015-12-09','2015-12-01','汉族','中共党员',12,'测试3',''),('bcbc281c-afaa-4f3f-80e7-3b1393da66ab','161210101','test','女','2015-12-10','2012-09-07','汉族','中共预备党员',10,'测试2','');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`userId`,`userName`,`password`) values (1,'admin','admin'),(101,'161200101','161200101'),(102,'161200102','161200102'),(103,'161200103','161200103'),(104,'161200104','161200104'),(105,'161200105','161200105'),(106,'161200106','161200106'),(107,'161200107','161200107'),(108,'161200108','161200108'),(109,'161200109','161200109'),(110,'161200110','161200110'),(111,'161200111','161200111'),(112,'161200112','161200112'),(113,'161200113','161200113'),(114,'161200114','161200114'),(115,'161200115','161200115'),(116,'161200116','161200116'),(117,'161200117','161200117'),(118,'161200118','161200118'),(119,'161200119','161200119'),(120,'161200120','161200120'),(121,'161200121','161200121'),(122,'161200122','161200122'),(123,'161200123','161200123'),(124,'161200124','161200124'),(125,'161200125','161200125'),(126,'161200126','161200126'),(127,'161200127','161200127'),(128,'161200128','161200128'),(129,'161200129','161200129'),(130,'161200130','161200130'),(131,'161200131','161200131'),(132,'161200132','161200132'),(133,'test1','1234');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
