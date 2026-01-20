package com.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class SubirImagenAlumno {

    public static void main(String[] args) {

        // Cargar configuración desde config.properties
        Properties props = new Properties();
        try (InputStream configStream = SubirImagenAlumno.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (configStream == null) {
                System.out.println("No se encontró config.properties");
                return;
            }

            props.load(configStream);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Obtener propiedades
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String sql = "INSERT INTO alumnos (nombre, apellidos, dni, imagen, tipo_imagen) VALUES (?, ?, ?, ?, ?)";

        // Leer imagen desde resources
        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(sql);
            InputStream is = SubirImagenAlumno.class
                    .getClassLoader()
                    .getResourceAsStream("imagenes/foto1.jpeg");
        ) {

            if (is == null) {
                System.out.println("No se encontró la imagen");
                return;
            }

            ps.setString(1, "Pedro");
            ps.setString(2, "Martínez López");
            ps.setString(3, "30778676T");
            ps.setBinaryStream(4, is);
            ps.setString(5, "image/jpeg");

            ps.executeUpdate();
            System.out.println("Imagen subida correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
