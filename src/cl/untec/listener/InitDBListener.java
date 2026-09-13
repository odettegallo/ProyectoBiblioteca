package cl.untec.listener;

import cl.untec.dao.Conexion;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class InitDBListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(">>> Inicializando Base de Datos H2...");
        
        try (Connection con = Conexion.getConexion();
             Statement stmt = con.createStatement()) {

            // 1. Crear tabla usuarios
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "usuario VARCHAR(50) NOT NULL, " +
                    "clave VARCHAR(50) NOT NULL" +
                    ")");

            // 2. Crear tabla libros
            stmt.execute("CREATE TABLE IF NOT EXISTS libros (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "titulo VARCHAR(150) NOT NULL, " +
                    "autor VARCHAR(100) NOT NULL, " +
                    "disponible BOOLEAN DEFAULT TRUE" +
                    ")");

            // 3. Crear tabla prestamos
            stmt.execute("CREATE TABLE IF NOT EXISTS prestamos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "id_usuario INT, " +
                    "id_libro INT, " +
                    "fecha_prestamo DATE, " +
                    "fecha_devolucion DATE, " +
                    "estado VARCHAR(20)" +
                    ")");

            // 4. Insertar usuario admin si no existe
            stmt.execute("MERGE INTO usuarios (id, usuario, clave) KEY(usuario) VALUES (1, 'admin', '1234')");

            // 5. Insertar libros de prueba si la tabla esta vacia
            stmt.execute("INSERT INTO libros (titulo, autor, disponible) " +
                    "SELECT 'Don Quijote de la Mancha', 'Miguel de Cervantes', true " +
                    "WHERE NOT EXISTS (SELECT 1 FROM libros)");
            
            stmt.execute("INSERT INTO libros (titulo, autor, disponible) " +
                    "SELECT 'El Principito', 'Antoine de Saint-Exupéry', true " +
                    "WHERE NOT EXISTS (SELECT 1 FROM libros)");

            stmt.execute("INSERT INTO libros (titulo, autor, disponible) " +
                    "SELECT 'Cien Años de Soledad', 'Gabriel García Márquez', true " +
                    "WHERE NOT EXISTS (SELECT 1 FROM libros)");

            System.out.println(">>> Base de Datos H2 Inicializada con exito.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Limpieza al detener la aplicacion
    }
}