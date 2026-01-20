package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class ObtenerImagenAlumnoConfig {

    public static void main(String[] args) {

        // DNI del alumno a recuperar
        String dni = "30778676T";

        // Leer propiedades desde resources/config.properties
        Properties props = new Properties();
        try (InputStream propStream = ObtenerImagenAlumnoConfig.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (propStream == null) {
                System.out.println("No se encontró config.properties");
                return;
            }

            props.load(propStream);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Leer configuración de conexión y ruta de imágenes
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        String rutaRelativa = props.getProperty("ruta.imagenes", "imagenes_bbdd");

        // Crear carpeta de salida si no existe
        File carpetaSalida = new File(System.getProperty("user.dir"), rutaRelativa);
        if (!carpetaSalida.exists()) carpetaSalida.mkdirs();

        // Conexión a la base de datos
        String sql = "SELECT imagen, tipo_imagen FROM alumnos WHERE dni = ?";
        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("imagen");
                String tipo = rs.getString("tipo_imagen"); // ej: image/jpeg

                if (is != null) {
                    // Detectar extensión a partir del tipo MIME
                    String extension = "jpg";
                    if ("image/png".equals(tipo)) extension = "png";

                    File salida = new File(carpetaSalida, dni + "." + extension);

                    try (FileOutputStream fos = new FileOutputStream(salida)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }

                    is.close();

                    System.out.println("Imagen guardada correctamente en: " + salida.getAbsolutePath());
                } else {
                    System.out.println("El alumno no tiene imagen");
                }
            } else {
                System.out.println("No existe alumno con ese DNI");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
