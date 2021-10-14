-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 27, 2018 at 01:52 AM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `book_organizerdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_name` varchar(150) NOT NULL,
  `admin_password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_name`, `admin_password`) VALUES
('admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `book_name` varchar(150) NOT NULL,
  `number_of_pages` int(4) NOT NULL,
  `book_link` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`book_name`, `number_of_pages`, `book_link`) VALUES
('Applied Calculus', 129, ''),
('Communication and information', 215, ''),
('Computer Architect', 103, ''),
('Data computing and information', 150, ''),
('Data Structure and algorithms', 180, ''),
('Liner Algebra', 132, ''),
('Networked Living', 175, ''),
('New Book', 254, ''),
('Relational Database Theory & Practice', 135, ''),
('Software Engineering ', 363, ''),
('Team Work ', 253, ''),
('Web Development using PHP', 450, ''),
('Web Technologies', 325, '.');

-- --------------------------------------------------------

--
-- Table structure for table `schedual`
--

CREATE TABLE `schedual` (
  `user_name` varchar(150) NOT NULL,
  `book_name` varchar(150) NOT NULL,
  `number_of_pages` int(4) NOT NULL,
  `days` int(4) DEFAULT NULL,
  `read_pages` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `schedual`
--

INSERT INTO `schedual` (`user_name`, `book_name`, `number_of_pages`, `days`, `read_pages`) VALUES
('Shaima123', 'Data computing and information', 150, 5, 31),
('Shaima123', 'Data Structure and algorithms', 180, 8, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `name` varchar(100) NOT NULL,
  `user_name` varchar(150) NOT NULL,
  `user_password` varchar(100) NOT NULL,
  `alarm_Time` time(6) NOT NULL DEFAULT '05:00:00.000000'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`name`, `user_name`, `user_password`, `alarm_Time`) VALUES
('Ahmed', 'Ahmed123', '123', '05:00:00.000000'),
('Ahmed', 'Ahmed234', '123', '05:00:00.000000'),
('Donia', 'Donia123', '123', '05:00:00.000000'),
('Dr.Malak', 'Dr.Malak', '233', '21:55:00.000000'),
('Mari', 'Maria2003', '1234', '05:00:00.000000'),
('Mona', 'Mona123', '124', '13:23:00.000000'),
('Mona', 'mona451', '123', '05:00:00.000000'),
('Sameera', 'Sameera123', '123', '05:00:00.000000'),
('Shaima', 'Shaima123', '123', '03:22:00.000000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_name`);

--
-- Indexes for table `schedual`
--
ALTER TABLE `schedual`
  ADD PRIMARY KEY (`book_name`,`user_name`),
  ADD KEY `user_name` (`user_name`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_name`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `schedual`
--
ALTER TABLE `schedual`
  ADD CONSTRAINT `schedual_ibfk_1` FOREIGN KEY (`book_name`) REFERENCES `book` (`book_name`),
  ADD CONSTRAINT `schedual_ibfk_2` FOREIGN KEY (`user_name`) REFERENCES `user` (`user_name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
