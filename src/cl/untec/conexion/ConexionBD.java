package cl.untec.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private ConexionBD() {}

    public static Connection getConexion() {
        Connection conexion = null; // Variable LOCAL, no estática
        try {
            Properties props = new Properties();
            InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties");
            
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo db.properties en src");
            }
            
            props.load(input);

            Class.forName(props.getProperty("db.driver"));
            conexion = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        return conexion;
    }
}