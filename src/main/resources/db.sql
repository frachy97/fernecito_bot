
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "-03:00"; /* time zome America/Argentina/BuenosAires */

--
-- Database: `ferreyra`
--

DROP DATABASE IF EXISTS ferreyra;
CREATE DATABASE ferreyra;
use ferreyra;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
 `id_user` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(40) NOT NULL,
 `password` varchar(40) NOT NULL,
 PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Table structure for table `credits`
--

CREATE TABLE `credits` (
 `id_credit` int(11) NOT NULL AUTO_INCREMENT,
 `hash_code` varchar(40) NOT NULL,
 `price` float(10) NOT NULL,
 `active` boolean NOT NULL,
 `date` date NOT NULL,
 `fk_id_user` int(11) NOT NULL,
 PRIMARY KEY (`id_credit`),
 FOREIGN KEY (`fk_id_user`) REFERENCES users(`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELIMITER $$
CREATE PROCEDURE `sp_add_credit`(p_user_id int, p_price double, p_active boolean, p_code VARCHAR(40))-- working
BEGIN
    DECLARE date datetime;
    set date = (select now());
    insert into credits (hash_code, price, active, date, fk_id_user)
        values(p_code, p_price, p_active, date, p_user_id);
        select LAST_INSERT_ID() as 'id_credit';
END $$




