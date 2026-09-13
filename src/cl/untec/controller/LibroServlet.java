package cl.untec.controller;

import cl.untec.dao.LibroDAO;
import cl.untec.model.Libro;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LibroServlet")
public class LibroServlet extends HttpServlet {

    private static final long serialVersionUID = 4640727471454815192L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        LibroDAO dao = new LibroDAO();
        String action = request.getParameter("action");

        // Acción Eliminar mediante GET
        if ("eliminar".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminarLibro(id);
                request.setAttribute("mensaje", "Libro eliminado correctamente.");
            } catch (Exception e) {
                request.setAttribute("error", "No se pudo eliminar el libro.");
            }
        }

        List<Libro> listaLibros = dao.listarLibros();
        request.setAttribute("libros", listaLibros);
        
     // Agrega esta limpieza antes de hacer request.getRequestDispatcher("/libros.jsp").forward(request, response);
        if (session.getAttribute("mensaje") != null) {
            request.setAttribute("mensaje", session.getAttribute("mensaje"));
            session.removeAttribute("mensaje");
        }
        if (session.getAttribute("error") != null) {
            request.setAttribute("error", session.getAttribute("error"));
            session.removeAttribute("error");
        }
        
        request.getRequestDispatcher("/libros.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        LibroDAO dao = new LibroDAO();

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        boolean disponible = request.getParameter("disponible") != null;

        if ("guardar".equals(action)) {
            Libro nuevoLibro = new Libro(0, titulo, autor, disponible);
            dao.agregarLibro(nuevoLibro);
            request.setAttribute("mensaje", "Libro agregado con éxito.");
        } else if ("actualizar".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Libro libroEditar = new Libro(id, titulo, autor, disponible);
                dao.actualizarLibro(libroEditar);
                request.setAttribute("mensaje", "Libro actualizado con éxito.");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de libro no válido.");
            }
        }

        // Refrescar el listado después del POST
        List<Libro> listaLibros = dao.listarLibros();
        request.setAttribute("libros", listaLibros);
        request.getRequestDispatcher("/libros.jsp").forward(request, response);
    }
}