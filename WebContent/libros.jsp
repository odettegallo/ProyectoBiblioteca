<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Biblioteca UNTEC - Libros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container py-4">

    <!-- Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Catálogo de Libros</h2>
        <div>
            <span class="me-3">Bienvenido, <strong><c:out value="${sessionScope.usuarioLogueado}"/></strong></span>
            <a href="PrestamoServlet?action=listar" class="btn btn-outline-primary btn-sm me-2">Ver Préstamos</a>
            <a href="LogoutServlet" class="btn btn-outline-danger btn-sm">Cerrar Sesión</a>
        </div>
    </div>

    <!-- Alertas (Verifica tanto request como sessionScope) -->
    <c:if test="${not empty sessionScope.mensaje || not empty requestScope.mensaje}">
        <div class="alert alert-success alert-dismissible fade show mb-3" role="alert">
            <c:out value="${not empty sessionScope.mensaje ? sessionScope.mensaje : requestScope.mensaje}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <%-- Limpiar mensaje de sesión para que no se siga mostrando al recargar --%>
        <c:remove var="mensaje" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.error || not empty requestScope.error}">
        <div class="alert alert-danger alert-dismissible fade show mb-3" role="alert">
            <c:out value="${not empty sessionScope.error ? sessionScope.error : requestScope.error}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="error" scope="session" />
    </c:if>

    <!-- Botón para Abrir Modal Nuevo Libro -->
    <div class="mb-3">
        <button type="button" class="btn btn-success" onclick="abrirModalNuevo()">
            + Nuevo Libro
        </button>
    </div>

    <!-- Tabla de Libros -->
    <table class="table table-striped table-hover align-middle">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Autor</th>
                <th>Estado</th>
                <th class="text-center">Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="libro" items="${libros}">
                <tr>
                    <td><c:out value="${libro.id}"/></td>
                    <td><c:out value="${libro.titulo}"/></td>
                    <td><c:out value="${libro.autor}"/></td>
                    <td>
                        <c:if test="${libro.disponible}">
                            <span class="badge bg-success">Disponible</span>
                        </c:if>
                        <c:if test="${!libro.disponible}">
                            <span class="badge bg-secondary">Prestado</span>
                        </c:if>
                    </td>
                    <td class="text-center">
                        <!-- Editar -->
                        <button type="button" class="btn btn-warning btn-sm me-1" 
                                onclick="editarLibro('${libro.id}', '<c:out value="${libro.titulo}"/>', '<c:out value="${libro.autor}"/>', ${libro.disponible})">
                            Editar
                        </button>
                        
                        <!-- Eliminar -->
                        <a href="LibroServlet?action=eliminar&id=${libro.id}" 
                           class="btn btn-danger btn-sm me-1" 
                           onclick="return confirm('¿Estás seguro de eliminar este libro?')">
                            Eliminar
                        </a>

                        <!-- Solicitar Préstamo -->
                        <c:if test="${libro.disponible}">
                            <form action="PrestamoServlet" method="post" class="d-inline">
                                <input type="hidden" name="action" value="prestar">
                                <input type="hidden" name="idLibro" value="${libro.id}">
                                <button type="submit" class="btn btn-info btn-sm text-white">Prestar</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Modal Formulario Libro -->
    <div class="modal fade" id="modalLibro" tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="LibroServlet" method="post" id="formLibro">
                    <input type="hidden" name="action" id="formAction" value="guardar">
                    <input type="hidden" name="id" id="libroId" value="0">

                    <div class="modal-header">
                        <h5 class="modal-title" id="modalTitle">Nuevo Libro</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Título</label>
                            <input type="text" name="titulo" id="libroTitulo" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Autor</label>
                            <input type="text" name="autor" id="libroAutor" class="form-control" required>
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" name="disponible" id="libroDisponible" value="true" class="form-check-input" checked>
                            <label class="form-check-label" for="libroDisponible">Disponible</label>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- JS Bootstrap via CDN oficial -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let modalElement;
        let bsModal;

        document.addEventListener("DOMContentLoaded", function () {
            modalElement = document.getElementById('modalLibro');
            bsModal = new bootstrap.Modal(modalElement);
        });

        function abrirModalNuevo() {
            document.getElementById('formAction').value = 'guardar';
            document.getElementById('libroId').value = '0';
            document.getElementById('libroTitulo').value = '';
            document.getElementById('libroAutor').value = '';
            document.getElementById('libroDisponible').checked = true;
            document.getElementById('modalTitle').innerText = 'Nuevo Libro';
            if(bsModal) bsModal.show();
        }

        function editarLibro(id, titulo, autor, disponible) {
            document.getElementById('formAction').value = 'actualizar';
            document.getElementById('libroId').value = id;
            document.getElementById('libroTitulo').value = titulo;
            document.getElementById('libroAutor').value = autor;
            document.getElementById('libroDisponible').checked = disponible;
            document.getElementById('modalTitle').innerText = 'Editar Libro';
            if(bsModal) bsModal.show();
        }
    </script>
</body>
</html>