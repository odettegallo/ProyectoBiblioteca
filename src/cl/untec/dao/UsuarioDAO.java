package cl.untec.dao;

import cl.untec.conexion.ConexionBD;
import cl.untec.model.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public Usuario validarUsuario(String username, String password) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
}