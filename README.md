# Proyecto Maven: Gestión de imágenes de alumnos en MySQL

Este proyecto de ejemplo en Java con Maven permite subir y recuperar imágenes de alumnos en una base de datos MySQL. La configuración es multiplataforma (Windows y Linux) y se gestiona mediante un archivo config.properties en resources.

## Estructura del proyecto
```
proyecto/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── SubirImagenAlumno.java
│   │   │   └── ObtenerImagenAlumnoConfig.java
│   │   └── resources/
│   │       ├── config.properties
│   │       └── imagenes/   <-- imágenes de ejemplo para subir
├── target/
├── pom.xml

Configuración (config.properties)
# Conexión a la base de datos MySQL
db.url=jdbc:mysql://localhost:3306/cole
db.user=root
db.password=Dam2425*

# Carpeta relativa para guardar las imágenes recuperadas
ruta.imagenes=imagenes_bbdd
```

db.url → URL de conexión a MySQL

db.user y db.password → credenciales

ruta.imagenes → carpeta donde se guardan las imágenes recuperadas (se crea automáticamente si no existe)

## Clases principales
- SubirImagenAlumno

Esta clase permite subir una imagen de ejemplo a la base de datos:

Lee una imagen desde src/main/resources/imagenes usando getResourceAsStream()

Inserta los datos en la tabla alumnos de la base de datos MySQL

La tabla debe tener al menos los campos: nombre, apellidos, dni, imagen (BLOB) y tipo_imagen (VARCHAR)

La conexión a MySQL se lee desde config.properties

Ejemplo de ejecución:

```mvn compile exec:java -Dexec.mainClass="com.example.SubirImagenAlumno"```

- ObtenerImagenAlumnoConfig

Esta clase permite recuperar la imagen de un alumno desde la base de datos:

Recibe el DNI del alumno a recuperar

Conecta a MySQL usando los datos de config.properties

Recupera el BLOB imagen y el tipo MIME tipo_imagen

Guarda la imagen en la carpeta indicada en ruta.imagenes con la extensión correcta (.jpg o .png)

Crea la carpeta automáticamente si no existe

Ejemplo de ejecución:

```mvn compile exec:java -Dexec.mainClass="com.example.ObtenerImagenAlumnoConfig"```

## Notas importantes

### Lectura de recursos:

Las imágenes para subir se leen desde resources usando getResourceAsStream().

Esto funciona en Windows, Linux y si el proyecto se empaqueta en un JAR.

### Escritura de archivos:

Las imágenes recuperadas se guardan en la carpeta definida en config.properties.

Nunca se escriben dentro de src ni resources en tiempo de ejecución.

### Extensiones automáticas:

La clase detecta si la imagen es JPEG o PNG a partir del campo tipo_imagen en la BBDD.

### Multiplataforma:

Todas las rutas usan System.getProperty("user.dir") y File para asegurar compatibilidad entre Windows y Linux.
