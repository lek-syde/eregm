  CREATE TABLE `healthcenter` (
  `health_center_id` int(11) NOT NULL,
  `active` varchar(5) NOT NULL,
  `health_center` varchar(255) DEFAULT NULL,
  `lga` varchar(255) DEFAULT NULL,
  `organizationuit` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`health_center_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `room` (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `health_center_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `timeslot` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=907 DEFAULT CHARSET=latin1;


CREATE TABLE `reservation` (
  `reservation_id` bigint(20) NOT NULL,
  `res_date` date DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `timeslot` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`reservation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SELECT * FROM emidnphcda.hibernate_sequence;



CREATE TABLE `user_profile` (
  `user_id` bigint NOT NULL,
  `area` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `dateofbirth` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estimatedage` int NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `frontline` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `healthfacility` varchar(255) DEFAULT NULL,
  `healthrelated` varchar(255) DEFAULT NULL,
  `idtype` varchar(255) DEFAULT NULL,
  `lga` varchar(255) DEFAULT NULL,
  `medicalconditionpresent` bit(1) NOT NULL,
  `occupation` varchar(255) DEFAULT NULL,
  `othername` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pregnant` varchar(255) DEFAULT NULL,
  `resaddress` varchar(255) DEFAULT NULL,
  `session` varchar(255) DEFAULT NULL,
  `stateresidence` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `phoneconf` varchar(45) DEFAULT NULL,
  `vac` varchar(45) DEFAULT NULL,
  `orgid` varchar(45) DEFAULT NULL,
  `facilityid` int DEFAULT NULL,
  `knowdob` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
-- Query: SELECT * FROM emidnphcda.healthcenter
LIMIT 0, 500

-- Date: 2021-08-01 13:25
*/


