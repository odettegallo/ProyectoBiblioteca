# Sistema de Gestión de Biblioteca - Untec

Aplicación Web Java (Servlets y JSP) desarrollada para la gestión y administración de préstamos de libros, usuarios y catálogo bibliográfico.

---

## 📋 Requisitos del Sistema

* **Java Development Kit (JDK):** Version 17, 21 o superior (Configurado con Apache Tomcat 9.0).
* **Servidor de Aplicaciones:** Apache Tomcat v9.0.
* **Base de Datos:** MySQL Server 8.0+.
* **IDE Recomendado:** Eclipse IDE for Enterprise Java and Web Developers.

---

## 🛠️ Configuración de la Base de Datos (MySQL)

### 1. Creación de la Base de Datos e Importación

1. Abre tu cliente de MySQL (MySQL Workbench, phpMyAdmin, DBeaver o la consola de comandos).
2. Crea la base de datos `untec_biblioteca`:
   ```sql
   CREATE DATABASE untec_biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE untec_biblioteca;
   ```
3. Ejecuta el archivo de script SQL adjunto en el proyecto (`WebContent/WEB-INF/schema.sql`):
   * **Desde MySQL Workbench:** Ve a `File` -> `Open SQL Script...`, selecciona `schema.sql` y ejecútalo.
   * **Desde Línea de Comandos:**
     ```bash
     mysql -u root -p untec_biblioteca < WebContent/WEB-INF/schema.sql
     ```

El script creará las tablas `usuarios`, `libros` y `prestamos`, e insertará datos iniciales de prueba y el usuario administrador.

---

## ⚙️ Configuración de Conexión en el Proyecto

Verifica que el archivo `db.properties` ubicado en la carpeta `src/` tenga los datos correspondientes a tu instalación local de MySQL:

```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/untec_biblioteca?serverTimezone=UTC&useSSL=false
db.user=root
db.password=TU_CONTRASEÑA_AQUÍ
```

*(Asegúrate de cambiar `TU_CONTRASEÑA_AQUÍ` por la contraseña de tu servidor MySQL local).*

---

## 🔑 Credenciales de Acceso

El script `schema.sql` incluye una cuenta de usuario por defecto para iniciar sesión en la aplicación:

* **Usuario:** `admin` 
* **Contraseña:** `1234`
* **Rol:** `BIBLIOTECARIO`

---

## 🚀 Despliegue y Ejecución

1. Importa el proyecto en **Eclipse IDE** (`File` -> `Import...` -> `Existing Projects into Workspace`).
2. Haz clic derecho sobre el proyecto en el *Project Explorer*.
3. Selecciona **Run As** -> **Run on Server**.
4. Selecciona tu servidor **Apache Tomcat v9.0** configurado y presiona **Finish**.
5. Abre tu navegador e ingresa a: `http://localhost:8080/ProyectoBiblioteca/`
