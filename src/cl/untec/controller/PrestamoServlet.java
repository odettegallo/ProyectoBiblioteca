package cl.untec.controller;

import cl.untec.dao.PrestamoDAO;
import cl.untec.model.Prestamo;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/PrestamoServlet")
public class PrestamoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión de usuario
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        PrestamoDAO prestamoDAO = new PrestamoDAO();

        if ("listar".equals(action)) {
            List<Prestamo> lista = prestamoDAO.listarPrestamos();
            request.setAttribute("prestamos", lista);
            request.getRequestDispatcher("/prestamos.jsp").forward(request, response);
        } else {
            response.sendRedirect("PrestamoServlet?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");
        PrestamoDAO prestamoDAO = new PrestamoDAO();

        if ("prestar".equals(action)) {
            try {
                int idLibro = Integer.parseInt(request.getParameter("idLibro"));
                
                // Obtener ID del usuario desde la sesión, si existe; de lo contrario se asigna 1 por defecto
                Integer idUsuario = (Integer) session.getAttribute("idUsuario");
                if (idUsuario == null) {
                    idUsuario = 1; 
                }

                boolean exito = prestamoDAO.registrarPrestamo(idUsuario, idLibro);
                if (exito) {
                    session.setAttribute("mensaje", "Préstamo registrado exitosamente.");
                } else {
                    session.setAttribute("error", "No se pudo registrar el préstamo.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            response.sendRedirect("LibroServlet");

        } else if ("devolver".equals(action)) {
            try {
                int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
                int idLibro = Integer.parseInt(request.getParameter("idLibro"));

                boolean exito = prestamoDAO.registrarDevolucion(idPrestamo, idLibro);
                if (exito) {
                    session.setAttribute("mensaje", "Devolución registrada exitosamente.");
                } else {
                    session.setAttribute("error", "No se pudo registrar la devolución.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            response.sendRedirect("PrestamoServlet?action=listar");
        }
    }
}