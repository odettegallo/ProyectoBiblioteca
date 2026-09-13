package cl.untec.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // =========================================================================
    // SWITCH DE CONFIGURACIÓN: Cambia a 'true' para usar H2, o 'false' para MySQL
    // =========================================================================
    private static final boolean USAR_H2 = false; 

    // --- Configuración MySQL ---
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/biblioteca_untec?serverTimezone=UTC";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "1234"; // Pon tu clave de MySQL

    // --- Configuración H2 (Modo Memoria con compatibilidad MySQL) ---
    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_URL = "jdbc:h2:mem:bibliotecadb;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String H2_USER = "sa";
    private static final String H2_PASS = "";

    public static Connection getConexion() throws SQLException {
        try {
            if (USAR_H2) {
                Class.forName(H2_DRIVER);
                return DriverManager.getConnection(H2_URL, H2_USER, H2_PASS);
            } else {
                Class.forName(MYSQL_DRIVER);
                return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el Driver JDBC: " + e.getMessage());
            throw new SQLException(e);
        }
    }
}