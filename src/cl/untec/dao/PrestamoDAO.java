package cl.untec.dao;

import cl.untec.conexion.ConexionBD;
import cl.untec.model.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // Registrar un nuevo préstamo
    public boolean registrarPrestamo(int idUsuario, int idLibro) {
        String sqlPrestamo = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo, estado) VALUES (?, ?, CURRENT_DATE, 'PRESTADO')";
        String sqlActualizarLibro = "UPDATE libros SET disponible = false WHERE id = ?";

        Connection conn = null;
        try {
            conn = ConexionBD.getConexion();
            conn.setAutoCommit(false); // Transacción para garantizar consistencia

            try (PreparedStatement ps1 = conn.prepareStatement(sqlPrestamo);
                 PreparedStatement ps2 = conn.prepareStatement(sqlActualizarLibro)) {

                ps1.setInt(1, idUsuario);
                ps1.setInt(2, idLibro);
                ps1.executeUpdate();

                ps2.setInt(1, idLibro);
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Registrar devolución de un libro
    public boolean registrarDevolucion(int idPrestamo, int idLibro) {
        String sqlDevolucion = "UPDATE prestamos SET fecha_devolucion = CURRENT_DATE, estado = 'DEVUELTO' WHERE id = ?";
        String sqlActualizarLibro = "UPDATE libros SET disponible = true WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlDevolucion);
                 PreparedStatement ps2 = conn.prepareStatement(sqlActualizarLibro)) {

                ps1.setInt(1, idPrestamo);
                ps1.executeUpdate();

                ps2.setInt(1, idLibro);
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Listar todos los préstamos
    public List<Prestamo> listarPrestamos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId(rs.getInt("id"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                p.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}