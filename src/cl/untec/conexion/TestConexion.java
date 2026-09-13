package cl.untec.conexion;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("Iniciando prueba de conexión...");
        
        try {
            Connection cn = ConexionBD.getConexion();
            if (cn != null && !cn.isClosed()) {
                System.out.println("¡CONEXIÓN EXITOSA A MYSQL!");
            } else {
                System.out.println("ERROR: La conexión devolvió NULL.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error durante la prueba:");
            e.printStackTrace();
        }
    }
}