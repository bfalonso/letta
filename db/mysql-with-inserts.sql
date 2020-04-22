CREATE DATABASE `letta`;

CREATE TABLE `letta`.`event` (
	`id` int NOT NULL AUTO_INCREMENT,
	`title` varchar(20) NOT NULL,
	`description` varchar(5000),
    `category` ENUM('sports', 'cinema', 'theater', 'music', 'literature', 'videogames', 'series', 'other') NOT NULL,
    `location` varchar(30) NOT NULL,
    `event_date` DATETIME NOT NULL,
    `creation_date` DATETIME NOT NULL,
    `capacity` int NOT NULL,
    `num_participants` int NOT NULL,
    `duration` int NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE USER 'letta'@'localhost' IDENTIFIED WITH mysql_native_password BY 'letta';
GRANT ALL ON `letta`.* TO 'letta'@'localhost';

INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (1,'Lesiones Waterpolo','Quedada para concienciar a los deportistas de que el waterpolo es un deporte de riesgo', 'sports', 'Pabellón de los Remedios', '2020-05-02 13:00:00', '2020-04-23 13:00:00', 10, 2, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (2,'Hitchcock','Reunión para que los amantes del director puedan intercambiar sus opiniones', 'cinema', 'Cafetería Charlotte', '2020-05-15 16:30:00', '2020-05-01 13:00:00', 6, 6, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (3,'Alberto Chicote','Reunión para debatir la influencia de Alberto Chicote en la gastronomía', 'other', 'Burger King', '2020-06-12 14:00:00', '2020-06-02 13:00:00', 7, 1, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (4,'Autotune','Crítica constructiva hacia los artistas que hacen uso de herramientas para modular la voz', 'music', 'Conservatorio', '2020-04-28 09:00:00', '2020-03-30 13:00:00', 10, 10, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (5,'Dalas Review','Recogida de firmas para expulsar a Dalas de la comunidad de Youtube', 'other', 'Centro Comercial', '2020-09-15 20:30:00', '2020-08-28 13:00:00', 20, 3, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (6,'Agatha Christie','Círculo de lectores a los que le interesen las obras de la autora', 'literature', 'Parque del Posío', '2020-09-21 18:30:00', '2020-09-13 13:00:00', 7, 3, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (7,'Los Serrano','Denunciar el mediocre final que tuvo esta prestigiosa serie española', 'series', 'Moruno', '2020-07-30 00:00:00', '2020-07-12 13:00:00', 18, 18, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (8,'Roleplay GTA V','Encuentro de jugadores interesados en crear un servidor nuevo', 'videogames', 'PC As Pontes', '2020-05-18 19:00:00', '2020-05-02 13:00:00', 8, 6, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (9,'Debate político', 'Reflexión sobre el auge de la derecha en España', 'other', 'Facultad de Educación', '2020-03-02 13:00:00', '2020-02-23 13:00:00', 10, 2, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (10,'E.T.','Quedada para los amantes del alienígena de la película dirigida por Steven Spielberg', 'cinema', 'Cafetería Charlotte', '2020-04-18 16:30:00', '2020-04-01 13:00:00', 8, 6, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (11,'Stop Azúcares','Crítica hacia la industria alimenticia por el abuso de adictivos en los productos que suministran', 'other', 'McAuto', '2020-02-29 14:00:00', '2020-02-11 13:00:00', 10, 9, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (12,'Cine de Barrio','Reunión a la que acudirá el referente mediático Jose Manuel Parada', 'cinema', 'Auditorio', '2020-11-28 09:00:00', '2020-11-02 13:00:00', 10, 10, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (13,'Discutiendo Lost','Encuentro para que los fanáticos de la serie puedan reflexionar acerca del final de la misma', 'series', 'Autobús urbano 7', '2020-04-16 17:30:00', '2020-04-15 13:00:00', 2, 1, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (14,'Cristiano Ronaldo','Quedada de fans aférrimos del astro futbolístico luso', 'sports', 'Campo de tiro Eiróas', '2020-06-21 20:30:00', '2020-06-12 13:00:00', 13, 3, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (15,'Supremas Móstoles','Reunión para recordar las joyas musicales que trajeron al mundo este trío de artistas', 'music', 'Moruno', '2020-07-31 00:00:00', '2020-07-12 13:00:00', 8, 7, 5);
INSERT INTO `letta`.`event` (`id`,`title`,`description`, `category`, `location`, `event_date`, `creation_date`, `capacity`, `num_participants`,`duration`) VALUES (16,'The Walking Dead','Recogida de firmas para cancelar la serie', 'series', 'Facultad de Derecho', '2020-01-18 19:00:00', '2020-01-02 13:00:00', 7, 5, 5);
