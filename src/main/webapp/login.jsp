<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PROHIRE - Iniciar Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #0f172a; color: #f8fafc; }
        .card-login { background-color: #1e293b; border: 1px solid #334155; border-radius: 12px; }
        .accent-text { color: #818cf8; }
        .btn-custom { background-color: #6366f1; border: 1px solid #6366f1; color: white; font-weight: bold; transition: all 0.3s ease; }
        .btn-custom:hover { background-color: #4f46e5; border-color: #4f46e5; color: white; box-shadow: 0 0 15px rgba(99, 102, 241, 0.4); }
    </style>
</head>
<body class="d-flex align-items-center vh-100">
    
    <div class="container card-login p-5 shadow-lg" style="max-width: 400px;">
        <h2 class="text-center mb-4 accent-text fw-bold">PROHIRE</h2>
        <h5 class="text-center mb-4 text-secondary">Acceso a la plataforma</h5>

        <% if(request.getParameter("logout") != null) { %>
            <div class="alert alert-info text-center bg-dark text-info border-info">Sesión cerrada.</div>
        <% } %>
        <% if(request.getParameter("error") != null) { %>
            <div class="alert alert-danger text-center bg-dark text-danger border-danger">Usuario o clave incorrectos.</div>
        <% } %>
        <% if(request.getParameter("registro") != null) { %>
            <div class="alert alert-success text-center bg-dark text-success border-success">¡Registro exitoso! Inicia sesión.</div>
        <% } %>

        <form action="LoginServlet" method="POST">
            <div class="mb-3">
                <label class="fw-bold">Email:</label>
                <input type="email" name="email" class="form-control bg-dark text-white border-secondary" required>
            </div>
            <div class="mb-4">
                <label class="fw-bold">Contraseña:</label>
                <input type="password" name="password" class="form-control bg-dark text-white border-secondary" required>
            </div>
            <button type="submit" class="btn btn-custom w-100 py-2">Entrar</button>
        </form>
        
        <div class="text-center mt-4">
            <p class="text-secondary">¿Aún no tienes cuenta? <br>
                <a href="registro.jsp" class="accent-text text-decoration-none fw-bold">Regístrate aquí</a>
            </p>
        </div>
    </div>
</body>
</html>