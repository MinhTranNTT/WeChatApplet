/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.18-log : Database - db_java1234_mall_v3
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_java1234_mall_v3` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_java1234_mall_v3`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(66) DEFAULT NULL,
  `password` varchar(66) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_admin` */

insert  into `t_admin`(`id`,`userName`,`password`) values (1,'java1234','123');

/*Table structure for table `t_bigtype` */

DROP TABLE IF EXISTS `t_bigtype`;

CREATE TABLE `t_bigtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `remark` varchar(765) DEFAULT NULL,
  `image` varchar(765) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `t_bigtype` */

insert  into `t_bigtype`(`id`,`name`,`remark`,`image`) values (1,'手机','手机描述','1.jpg'),(2,'电脑平板','电脑平板描述','2.jpg'),(3,'智能穿戴','智能穿戴描述','3.jpg'),(4,'电视','电视描述','4.jpg'),(5,'大家电','大家电描述','5.jpg'),(6,'小家电','小家电描述','6.jpg'),(7,'智能家居','智能家居描述','7.jpg'),(8,'户外出行','户外出行描述','8.jpg'),(9,'日用百货','日用百货描述','9.jpg'),(10,'儿童用品','儿童用品描述','10.jpg'),(11,'是','xxx','20220528044438000000203.jpg'),(12,'1244','1244','20220528044651000000389.jpg'),(14,'11','11','20220528064123000000314.jpg');

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(300) DEFAULT NULL,
  `userId` varchar(600) DEFAULT NULL,
  `totalPrice` decimal(8,2) DEFAULT NULL,
  `address` varchar(900) DEFAULT NULL,
  `consignee` varchar(60) DEFAULT NULL,
  `telNumber` varchar(60) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `payDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8;

/*Data for the table `t_order` */

insert  into `t_order`(`id`,`orderNo`,`userId`,`totalPrice`,`address`,`consignee`,`telNumber`,`createDate`,`payDate`,`status`) values (121,'JAVA20220407093651000000692','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 09:36:52',NULL,1),(122,'JAVA20220407093800000000948','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 09:38:01',NULL,1),(123,'JAVA20220407093836000000324','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 09:38:36',NULL,1),(124,'JAVA20220407094406000000613','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 09:44:07',NULL,1),(125,'JAVA20220407094622000000495','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 09:46:22',NULL,1),(126,'JAVA20220407094734000000023','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.23','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 09:47:34',NULL,1),(127,'JAVA20220407100032000000729','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 10:00:33',NULL,1),(128,'JAVA20220407100118000000148','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 10:01:18',NULL,1),(129,'JAVA20220407100237000000790','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 10:02:38',NULL,1),(130,'JAVA20220407100418000000063','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 10:04:18',NULL,1),(131,'JAVA20220407100453000000777','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 10:04:54',NULL,1),(132,'JAVA20220407100618000000747','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-07 10:06:19',NULL,1),(133,'JAVA20220409094845000000147','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-09 09:48:45',NULL,1),(134,'JAVA20220409095845000000297','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-09 09:58:45',NULL,1),(135,'JAVA20220409100122000000666','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-09 10:01:23',NULL,1),(136,'JAVA20220409100232000000234','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-09 10:02:32',NULL,1),(137,'JAVA20220409100318000000562','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-09 10:03:19',NULL,1),(138,'JAVA20220409100423000000646','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-09 10:04:24',NULL,1),(139,'JAVA20220410091131000000208','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:11:31',NULL,1),(140,'JAVA20220410091215000000916','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:12:16',NULL,1),(141,'JAVA20220410091312000000191','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:13:12',NULL,1),(142,'JAVA20220410093110000000369','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:31:10',NULL,1),(143,'JAVA20220410093531000000216','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:35:31',NULL,1),(144,'JAVA20220410094037000000817','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:40:38',NULL,1),(145,'JAVA20220410094504000000478','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:45:04',NULL,1),(146,'JAVA20220410095043000000380','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-10 09:50:43','2022-04-10 09:50:52',2),(147,'JAVA20220412090333000000654','o30ur5JpAsAUyGBkR0uW4IxvahR8','12498.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-12 09:03:34','2022-04-12 09:03:44',2),(148,'JAVA20220412090939000000602','o30ur5JpAsAUyGBkR0uW4IxvahR8','6998.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-12 09:09:40','2022-04-12 09:09:51',2),(149,'JAVA20220412091429000000834','o30ur5JpAsAUyGBkR0uW4IxvahR8','13497.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-12 09:14:30','2022-04-12 09:14:44',3),(150,'JAVA20220415092027000000571','o30ur5JpAsAUyGBkR0uW4IxvahR8','2599.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-15 09:20:28',NULL,3),(151,'JAVA20220417101545000000145','o30ur5JpAsAUyGBkR0uW4IxvahR8','3799.00','广东省广州市海珠区新港中路397号','张三','020-81167888','2022-04-17 10:15:45',NULL,2);

/*Table structure for table `t_order_detail` */

DROP TABLE IF EXISTS `t_order_detail`;

CREATE TABLE `t_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mId` int(11) DEFAULT NULL,
  `goodsId` int(11) DEFAULT NULL,
  `goodsNumber` int(11) DEFAULT NULL,
  `goodsPrice` decimal(10,0) DEFAULT NULL,
  `goodsName` varchar(300) DEFAULT NULL,
  `goodsPic` varchar(765) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8;

/*Data for the table `t_order_detail` */

insert  into `t_order_detail`(`id`,`mId`,`goodsId`,`goodsNumber`,`goodsPrice`,`goodsName`,`goodsPic`) values (158,121,14,1,'2599','Xiaomi Civi','9.png'),(159,122,14,1,'2599','Xiaomi Civi','9.png'),(160,123,14,1,'2599','Xiaomi Civi','9.png'),(161,124,14,1,'2599','Xiaomi Civi','9.png'),(162,125,14,1,'2599','Xiaomi Civi','9.png'),(163,126,14,1,'2599','Xiaomi Civi','9.png'),(164,127,14,1,'2599','Xiaomi Civi','9.png'),(165,128,14,1,'2599','Xiaomi Civi','9.png'),(166,129,14,1,'2599','Xiaomi Civi','9.png'),(167,130,14,1,'2599','Xiaomi Civi','9.png'),(168,131,14,1,'2599','Xiaomi Civi','9.png'),(169,132,14,1,'2599','Xiaomi Civi','9.png'),(170,133,14,1,'2599','Xiaomi Civi','9.png'),(171,134,14,1,'2599','Xiaomi Civi','9.png'),(172,135,14,1,'2599','Xiaomi Civi','9.png'),(173,136,14,1,'2599','Xiaomi Civi','9.png'),(174,137,14,1,'2599','Xiaomi Civi','9.png'),(175,138,14,1,'2599','Xiaomi Civi','9.png'),(176,139,14,1,'2599','Xiaomi Civi','9.png'),(177,140,14,1,'2599','Xiaomi Civi','9.png'),(178,141,14,1,'2599','Xiaomi Civi','9.png'),(179,142,14,1,'2599','Xiaomi Civi','9.png'),(180,143,14,1,'2599','Xiaomi Civi','9.png'),(181,144,14,1,'2599','Xiaomi Civi','9.png'),(182,145,14,1,'2599','Xiaomi Civi','9.png'),(183,146,14,1,'2599','Xiaomi Civi','9.png'),(184,147,6,1,'4499','Xiaomi 11 Pro','1.png'),(185,147,7,1,'7999','Xiaomi MIX FOLD折叠屏手机','2.png'),(186,148,6,1,'4499','Xiaomi 11 Pro','1.png'),(187,148,12,1,'2499','Redmi  K40 Pro 系列','8.png'),(188,149,7,1,'7999','Xiaomi MIX FOLD折叠屏手机','2.png'),(189,149,12,1,'2499','Redmi  K40 Pro 系列','8.png'),(190,149,9,1,'2999','Xiaomi 10S','4.png'),(191,150,14,1,'2599','Xiaomi Civi','9.png'),(192,151,4,1,'3799','Xiaomi 11','6.png');

/*Table structure for table `t_product` */

DROP TABLE IF EXISTS `t_product`;

CREATE TABLE `t_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `proPic` varchar(765) DEFAULT NULL,
  `isHot` bit(1) DEFAULT NULL,
  `isSwiper` bit(1) DEFAULT NULL,
  `swiperPic` varchar(765) DEFAULT NULL,
  `swiperSort` int(11) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `hotDateTime` datetime DEFAULT NULL,
  `productIntroImgs` varchar(6000) DEFAULT NULL,
  `productParaImgs` varchar(6000) DEFAULT NULL,
  `description` varchar(6000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `t_product` */

insert  into `t_product`(`id`,`name`,`price`,`stock`,`proPic`,`isHot`,`isSwiper`,`swiperPic`,`swiperSort`,`typeId`,`hotDateTime`,`productIntroImgs`,`productParaImgs`,`description`) values (1,'小米电视大师 82英寸','10999',3451,'21.png','\0','','2.jpg',3,14,NULL,'\"\"','\"\"','\"\"'),(4,'Xiaomi 11','3799',3232,'6.png','','\0','default.jpg',0,2,'2021-07-28 21:36:34','<img width=\'100%\' src=\'http://localhost:8080/image/productIntroImgs/11.jpg\'></img>','<img width=\'100%\' src=\'http://localhost:8080/image/productParaImgs/11.jpg\'></img>','「全版本领券立减200元，券后价3299元起；享至高24期免息；赠手机保护壳*1;【全款支付套装】赠果冻包」'),(5,'Redmi K40 游戏增强版','2299',2005,'11.png','\0','','1.jpg',1,39,NULL,'\"\"','\"\"','\"\"'),(6,'Xiaomi 11 Pro','4499',2343,'1.png','','\0','default.jpg',0,2,'2021-09-28 21:36:34','\"\"',NULL,NULL),(7,'Xiaomi MIX FOLD折叠屏手机','7999',2222,'2.png','','\0','default.jpg',0,3,'2021-10-28 21:36:34','\"\"',NULL,NULL),(8,'Note 9 5G','1199',1111,'3.png','','\0','default.jpg',0,40,'2021-09-28 21:36:34','\"\"',NULL,NULL),(9,'Xiaomi 10S','2999',1111,'4.png','','\0','default.jpg',0,2,'2021-12-28 21:36:34','\"\"',NULL,NULL),(10,'Note 9 Pro 5G','1399',2222,'5.png','','\0','default.jpg',0,40,'2021-11-28 21:36:34','\"\"',NULL,NULL),(11,'黑鲨4','2499',3322,'7.png','','\0','default.jpg',0,41,'2021-11-28 21:36:34','\"\"',NULL,NULL),(12,'Redmi  K40 Pro 系列','2499',3244,'8.png','','\0','default.jpg',0,39,'2021-11-28 21:36:34',NULL,NULL,NULL),(14,'Xiaomi Civi','2599',4444,'9.png','\0','\0','default.jpg',0,1,NULL,'<img width=\'100%\' src=\'http://localhost:8080/image/productIntroImgs/1.jpg\'></img><img width=\'100%\' src=\'http://localhost:8080/image/productIntroImgs/2.jpg\'></img><img width=\'100%\' src=\'http://localhost:8080/image/productIntroImgs/3.jpg\'></img>','<img width=\'100%\' src=\'http://localhost:8080/image/productParaImgs/1.jpg\'></img><img width=\'100%\' src=\'http://localhost:8080/image/productParaImgs/2.jpg\'></img>','「购机至高享24期免息；赠Redmi AirDots 2真无线蓝牙耳机；赠Keep会员7天体验卡；+110元得Air2 SE蓝牙耳机」'),(15,'Xiaomi 11 Ultra','5499',4444,'10.png','\0','\0','default.jpg',0,2,NULL,NULL,NULL,NULL),(16,'小米平板5','1999',444,'13.png','\0','\0','default.jpg',0,5,NULL,'<img src=\'http://localhost:8080/image/productIntroImgs/111.jpg\'></img><img width=\'100%\' src=\'http://localhost:8080/image/productIntroImgs/222.jpg\'></img><img width=\'100%\' src=\'http://localhost:8080/image/productIntroImgs/333.jpg\'></img>','<img width=\'100%\' src=\'http://localhost:8080/image/productParaImgs/111.jpg\'></img>','11英寸大屏 2.5K超清显示 120Hz高刷新率'),(17,'小米平板5 Pro','2499',444,'14.png','\0','\0','default.jpg',0,5,NULL,NULL,NULL,NULL),(19,'RedmiBook Pro 15 增强版','5299',444,'15.png','\0','\0','default.jpg',0,6,NULL,NULL,NULL,NULL),(20,'Redmi G 2021','6499',1999,'16.png','\0','','3.jpg',2,6,NULL,NULL,NULL,NULL),(21,'Redmi G 2021 锐龙版','7499',2000,'17.png','\0','\0','default.jpg',0,6,NULL,NULL,NULL,NULL),(22,'RedmiBook Pro 14 增强版','4999',777,'18.png','\0','\0','default.jpg',0,6,NULL,NULL,NULL,NULL),(23,'小米笔记本 Pro 14 锐龙版','5499',666,'19.png','\0','\0','default.jpg',0,7,NULL,NULL,NULL,NULL),(24,'小米笔记本 Pro 14 增强版','5499',666,'20.png','\0','\0','default.jpg',0,7,NULL,NULL,NULL,NULL),(26,'1','1',1,'default.jpg','\0','\0','default.jpg',0,6,NULL,'1','1',NULL),(27,'1','2',3,'default.jpg','\0','','20220608091501000000134.jfif',-1,6,NULL,'5','6','4'),(28,'2','3',4,'20220607090750000000962.jpg','\0','\0','default.jpg',0,6,NULL,'6','7','5'),(29,'1','1',1,'20220607090724000000320.jpg','\0','\0','default.jpg',0,11,NULL,'1','1','1'),(30,'1','1',1,'default.jpg','','\0','default.jpg',0,6,'2022-06-13 08:31:58','<p>22</p>','<p>33</p>','1'),(56,'233355','233355',23335,'default.jpg','\0','\0','default.jpg',0,1,NULL,'<p>5</p>','<p>5</p>','2335');

/*Table structure for table `t_product_swiper_image` */

DROP TABLE IF EXISTS `t_product_swiper_image`;

CREATE TABLE `t_product_swiper_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(765) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `t_product_swiper_image` */

insert  into `t_product_swiper_image`(`id`,`image`,`sort`,`productId`) values (1,'1.jpg',1,14),(2,'2.jpg',2,14),(3,'3.jpg',3,14),(4,'4.jpg',4,14),(5,'5.jpg',5,14),(6,'6.jpg',6,14),(7,'7.jpg',7,14),(8,'11.jpg',1,4),(9,'22.jpg',2,4),(10,'33.jpg',3,4),(11,'44.jpg',4,4),(12,'111.jpg',1,16),(13,'222.jpg',2,16),(14,'333.jpg',3,16),(17,'20220609091415000000934.jpg',8,4),(18,'20220609091605000000313.jpg',0,1),(19,'20220609091615000000089.jpg',0,5);

/*Table structure for table `t_smalltype` */

DROP TABLE IF EXISTS `t_smalltype`;

CREATE TABLE `t_smalltype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `remark` varchar(765) DEFAULT NULL,
  `bigTypeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

/*Data for the table `t_smalltype` */

insert  into `t_smalltype`(`id`,`name`,`remark`,`bigTypeId`) values (1,'Xiaomi Civi','Xiaomi Civi',1),(2,'Xiaomi 数字系列','Xiaomi 数字系列',1),(3,'Xiaomi MIX系列','Xiaomi MIX系列',1),(5,'小米平板','小米平板',2),(6,'RedmiBook','RedmiBook',2),(7,'小米笔记本','小米笔记本',2),(10,'耳机','耳机',3),(11,'手表','手表',3),(12,'手环','手环',3),(13,'电视','电视',4),(14,'小米电视大师','小米电视大师',4),(15,'电视配件','电视配件',4),(16,'空调','空调',5),(17,'洗衣机','洗衣机',5),(18,'冰箱','冰箱',5),(19,'厨房大电','厨房大电',5),(20,'厨房小电','厨房小电',6),(21,'清洁电器','清洁电器',6),(22,'环境电器','环境电器',6),(23,'生活电器','生活电器',6),(24,'小爱音箱','小爱音箱',7),(25,'路由器','路由器',7),(26,'智能安防','智能安防',7),(27,'智能控制','智能控制',7),(28,'户外出行','户外出行',8),(29,'箱包','箱包',8),(30,'家具日用','家具日用',9),(31,'防护清洁','防护清洁',9),(32,'会员定制','会员定制',9),(33,'个人护理','个人护理',9),(34,'健康','健康',9),(35,'鞋服配饰','鞋服配饰',9),(36,'床品家居','床品家居',9),(37,'礼品周边','礼品周边',9),(38,'儿童用品','儿童用品',10),(39,'Redmi K系列','K系列',1),(40,'Redmi Note系列','Note系列',1),(41,'游戏手机','游戏手机',1),(44,'22','22',3),(45,'是','3 是',2),(46,'ewew','ew',3),(47,'rrr','rrr',2),(48,'43','4343',2),(49,'5','55',4),(50,'xxx','xxx2',2),(51,'是311','33311',1);

/*Table structure for table `t_wxuserinfo` */

DROP TABLE IF EXISTS `t_wxuserinfo`;

CREATE TABLE `t_wxuserinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(90) DEFAULT NULL,
  `nickName` varchar(150) DEFAULT NULL,
  `avatarUrl` varchar(600) DEFAULT NULL,
  `registerDate` datetime DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `t_wxuserinfo` */

insert  into `t_wxuserinfo`(`id`,`openid`,`nickName`,`avatarUrl`,`registerDate`,`lastLoginDate`) values (1,'o30ur5JpAsAUyGBkR0uW4IxvahR8','小锋一号@java1234','https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132','2022-04-03 09:55:05','2022-05-20 08:58:37'),(2,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(3,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(4,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(5,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(6,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(7,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(8,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(9,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(10,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL),(11,NULL,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/GJeHhQSiczuph0tg4JyleLeDUnIghqXlQBiaUAJMvia8GRSjibYdPmFQrvPic6sAhyTz4C8Ivu1aWah3KpFMBdsPibEw/132',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
