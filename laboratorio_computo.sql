-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 04, 2022 at 05:18 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `laboratorio_computo`
--

-- --------------------------------------------------------

--
-- Table structure for table `estudiantes`
--

CREATE TABLE `estudiantes` (
  `id_estudiante` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `no_control` varchar(8) NOT NULL,
  `password` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `estudiantes`
--

INSERT INTO `estudiantes` (`id_estudiante`, `nombre`, `apellidos`, `no_control`, `password`) VALUES
(1, 'Usuario', 'Anonimo', 'N/A', '809916'),
(2, 'Fernando Alberto', 'Hernandez Quijano', '18530399', '2129'),
(3, 'JESSICA', 'MIRANDA', '09530198', '1234'),
(4, 'ANGEL ARTURO', 'SALAZAR', '10530203', '1234'),
(5, 'GERTRUDIS GUADALUPE', 'RODRIGUEZ', '12530488', '1234'),
(6, 'NORMAN GEOVANNI', 'UC', '12530758', '1234'),
(7, 'ONESIES AMAILY', 'GOMEZ', '13530194', '1234'),
(8, 'JACOBO DE JESUS', 'YAH', '13530455', '1234'),
(9, 'DANIEL ALONZO', 'BERNAL', '14530067', '1234'),
(10, 'DONALDO JAVIER', 'JIMENEZ', '14530133', '1234'),
(11, 'JORGE MARCOS', 'MEJIA', '14530149', '1234'),
(12, 'JORGE MARTIN', 'GIL', '14530255', '1234'),
(13, 'EDUARDO', 'SILICEO', '14530312', '1234'),
(14, 'ANTHONY JESUS', 'ANGHEVEN', '14530435', '1234'),
(15, 'JOSE MIGUEL', 'BALAM', '14530440', '1234'),
(16, 'ROBERTO CARLOS', 'GARCIA', '14530466', '1234'),
(17, 'FABIOLA', 'MARTINEZ', '14530765', '1234'),
(18, 'ARTURO ALEJANDRO', 'POOT', '15530206', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `sesiones`
--

CREATE TABLE `sesiones` (
  `id_sesion` int(11) NOT NULL,
  `fecha` date NOT NULL DEFAULT curdate(),
  `hora_inicial` time DEFAULT NULL,
  `hora_final` time DEFAULT curtime(),
  `folio` varchar(10) DEFAULT NULL,
  `aula` int(11) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `id_estudiante` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Indexes for dumped tables
--

--
-- Indexes for table `estudiantes`
--
ALTER TABLE `estudiantes`
  ADD PRIMARY KEY (`id_estudiante`);

--
-- Indexes for table `sesiones`
--
ALTER TABLE `sesiones`
  ADD PRIMARY KEY (`id_sesion`),
  ADD KEY `id_estudiante` (`id_estudiante`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `estudiantes`
--
ALTER TABLE `estudiantes`
  MODIFY `id_estudiante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2887;

--
-- AUTO_INCREMENT for table `sesiones`
--
ALTER TABLE `sesiones`
  MODIFY `id_sesion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=578;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sesiones`
--
ALTER TABLE `sesiones`
  ADD CONSTRAINT `sesiones_ibfk_1` FOREIGN KEY (`id_estudiante`) REFERENCES `estudiantes` (`id_estudiante`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
