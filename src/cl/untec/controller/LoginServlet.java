package cl.untec.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8175431707590682147L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        // Autenticación básica (ajusta según tus credenciales de prueba)
        if ("admin".equals(usuario) && "1234".equals(clave)) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            response.sendRedirect("LibroServlet");
        } else {
            request.setAttribute("error", "Credenciales incorrectas");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}