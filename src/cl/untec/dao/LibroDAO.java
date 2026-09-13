package cl.untec.dao;

import cl.untec.conexion.ConexionBD;
import cl.untec.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // READ ALL
    public List<Libro> listarLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getBoolean("disponible")
                );
                lista.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // READ BY ID
    public Libro obtenerPorId(int id) {
        Libro libro = null;
        String sql = "SELECT * FROM libros WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponible")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libro;
    }

    // CREATE
    public boolean agregarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, disponible) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setBoolean(3, libro.isDisponible());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, disponible = ? WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setBoolean(3, libro.isDisponible());
            ps.setInt(4, libro.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean eliminarLibro(int id) {
        String sqlBorrarPrestamos = "DELETE FROM prestamos WHERE id_libro = ?";
        String sqlBorrarLibro = "DELETE FROM libros WHERE id = ?";

        try (Connection conn = ConexionBD.getConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlBorrarPrestamos);
                 PreparedStatement ps2 = conn.prepareStatement(sqlBorrarLibro)) {

                // 1. Eliminar referencias en tabla prestamos
                ps1.setInt(1, id);
                ps1.executeUpdate();

                // 2. Eliminar el libro
                ps2.setInt(1, id);
                int filasAfectadas = ps2.executeUpdate();

                conn.commit();
                return filasAfectadas > 0;
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
}