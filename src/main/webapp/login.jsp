<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PROHIRE - Iniciar Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #121212; color: white; }
        .card-login { background-color: #1e1e1e; border: 1px solid #333; border-radius: 10px; }
        .neon-text { color: #00e5ff; text-shadow: 0 0 10px #00e5ff; }
        .btn-neon { background-color: transparent; border: 1px solid #00e5ff; color: #00e5ff; font-weight: bold; }
        .btn-neon:hover { background-color: #00e5ff; color: #121212; box-shadow: 0 0 15px #00e5ff; }
    </style>
</head>
<body class="d-flex align-items-center vh-100">
    
    <div class="container card-login p-5 shadow-lg" style="max-width: 400px;">
        <h2 class="text-center mb-4 neon-text fw-bold">PROHIRE</h2>
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
            <button type="submit" class="btn btn-neon w-100">Entrar</button>
        </form>
        
        <div class="text-center mt-4">
            <p class="text-secondary">¿Aún no tienes cuenta? <br>
                <a href="registro.jsp" class="neon-text text-decoration-none fw-bold">Regístrate aquí</a>
            </p>
        </div>
    </div>
</body>
</html>