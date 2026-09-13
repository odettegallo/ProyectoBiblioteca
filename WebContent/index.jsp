<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Biblioteca UNTEC - Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center vh-100">
    <div class="container" style="max-width: 400px;">
        <div class="card shadow p-4">
            <h3 class="text-center mb-4">Biblioteca UNTEC</h3>
            
            <!-- Alerta de Errores -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <c:out value="${error}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <!-- Alerta de Mensajes / Éxito (ej. Cierre de sesión) -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <c:out value="${mensaje}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <form action="LoginServlet" method="post">
                <div class="mb-3">
                    <label for="usuario" class="form-label">Usuario</label>
                    <input type="text" id="usuario" name="usuario" class="form-control" placeholder="Ej: admin" required autofocus>
                </div>
                <div class="mb-3">
                    <label for="clave" class="form-label">Contraseña</label>
                    <input type="password" id="clave" name="clave" class="form-control" placeholder="••••••••" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Ingresar</button>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>