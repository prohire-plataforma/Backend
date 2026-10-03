<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PROHIRE - Registro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #121212; color: white; }
        .card-registro { background-color: #1e1e1e; border: 1px solid #333; border-radius: 10px; }
        .neon-text { color: #00e5ff; text-shadow: 0 0 10px #00e5ff; }
        .btn-neon { background-color: transparent; border: 1px solid #00e5ff; color: #00e5ff; font-weight: bold; }
        .btn-neon:hover { background-color: #00e5ff; color: #121212; box-shadow: 0 0 15px #00e5ff; }
    </style>
</head>
<body class="d-flex align-items-center vh-100">
    <div class="container card-registro p-5 shadow-lg" style="max-width: 500px;">
        <h2 class="text-center mb-4 neon-text fw-bold">PROHIRE</h2>
        <h5 class="text-center mb-4 text-secondary">Crear nueva cuenta</h5>

        <form action="UsuarioServlet" method="POST">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="fw-bold">Nombre Completo:</label>
                    <input type="text" name="nombre" class="form-control bg-dark text-white border-secondary" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="fw-bold">Email:</label>
                    <input type="email" name="email" class="form-control bg-dark text-white border-secondary" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="fw-bold">Contraseña:</label>
                    <input type="password" name="password" class="form-control bg-dark text-white border-secondary" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="fw-bold">Tipo de Perfil:</label>
                    <select name="rol" class="form-select bg-dark text-white border-secondary">
                        <option value="profesional">Profesional</option>
                        <option value="empresa">Empresa</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="fw-bold">Profesión / Industria:</label>
                    <input type="text" name="profesion" class="form-control bg-dark text-white border-secondary">
                </div>
                <div class="col-md-6 mb-4">
                    <label class="fw-bold">Teléfono:</label>
                    <input type="text" name="telefono" class="form-control bg-dark text-white border-secondary">
                </div>
            </div>
            <button type="submit" class="btn btn-neon w-100">Registrarse</button>
        </form>

        <div class="text-center mt-4">
            <p class="text-secondary">¿Ya tienes cuenta? <br>
                <a href="login.jsp" class="neon-text text-decoration-none fw-bold">Inicia Sesión</a>
            </p>
        </div>
    </div>
</body>
</html>