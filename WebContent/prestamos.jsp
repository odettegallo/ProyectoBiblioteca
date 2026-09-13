<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Biblioteca UNTEC - Préstamos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container py-4">

    <!-- Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Gestión de Préstamos</h2>
        <div>
            <a href="LibroServlet?action=listar" class="btn btn-outline-secondary btn-sm me-2">Volver a Libros</a>
            <a href="LogoutServlet" class="btn btn-outline-danger btn-sm">Cerrar Sesión</a>
        </div>
    </div>

    <!-- Alertas -->
    <c:if test="${not empty mensaje}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <c:out value="${mensaje}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Tabla de Préstamos -->
    <table class="table table-striped table-hover align-middle">
        <thead class="table-dark">
            <tr>
                <th>ID Préstamo</th>
                <th>ID Usuario</th>
                <th>ID Libro</th>
                <th>Fecha Préstamo</th>
                <th>Fecha Devolución</th>
                <th>Estado</th>
                <th class="text-center">Acción</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="prestamo" items="${prestamos}">
                <tr>
                    <td><c:out value="${prestamo.id}"/></td>
                    <td><c:out value="${prestamo.idUsuario}"/></td>
                    <td><c:out value="${prestamo.idLibro}"/></td>
                    <td><c:out value="${prestamo.fechaPrestamo}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty prestamo.fechaDevolucion}">
                                <c:out value="${prestamo.fechaDevolucion}"/>
                            </c:when>
                            <c:otherwise>---</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${prestamo.estado == 'PRESTADO'}">
                            <span class="badge bg-warning text-dark">PRESTADO</span>
                        </c:if>
                        <c:if test="${prestamo.estado == 'DEVUELTO'}">
                            <span class="badge bg-success">DEVUELTO</span>
                        </c:if>
                    </td>
                    <td class="text-center">
                        <c:if test="${prestamo.estado == 'PRESTADO'}">
                            <form action="PrestamoServlet" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="devolver">
                                <input type="hidden" name="idPrestamo" value="${prestamo.id}">
                                <input type="hidden" name="idLibro" value="${prestamo.idLibro}">
                                <button type="submit" class="btn btn-primary btn-sm">Devolver Libro</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>