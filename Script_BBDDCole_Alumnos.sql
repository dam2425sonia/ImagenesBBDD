DROP DATABASE IF EXISTS cole;
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS cole CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;

USE cole;

-- Crear la tabla de alumnos con imagen
CREATE TABLE alumnos (
    id_alumno INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE,
    fecha_nacimiento DATE,
    telefono VARCHAR(20),
    curso VARCHAR(50),
    imagen LONGBLOB,
    tipo_imagen VARCHAR(50),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
