<%@page import="com.prohire.model.*"%>
<%@page import="com.prohire.dao.*"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Verificación de sesión y rol
    Usuario u = (Usuario) session.getAttribute("userLog");
    if (u == null || !u.getRol().equals("empresa")) { 
        response.sendRedirect("login.jsp"); 
        return; 
    }
    
    VacanteDAO vDao = new VacanteDAO();
    PostulacionDAO pDao = new PostulacionDAO();
    List<Vacante> misVacantes = vDao.listarPorEmpresa(u.getId_usuario());
    List<Postulacion> candidatos = pDao.listarPorEmpresa(u.getId_usuario());
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PROHIRE - Panel Empresa</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #121212; color: white; }
        .navbar-custom { background-color: #0d0d0d; border-bottom: 2px solid #00e5ff; }
        .neon-text { color: #00e5ff; }
        .card-custom { background-color: #1e1e1e; border: 1px solid #333; }
        .table-custom { color: white; }
        .table-custom th { color: #00e5ff; }
        .btn-neon { background-color: transparent; border: 1px solid #00e5ff; color: #00e5ff; font-weight: bold; }
        .btn-neon:hover { background-color: #00e5ff; color: #121212; }
    </style>
</head>
<body>
    <nav class="navbar navbar-custom p-3 shadow">
        <div class="container-fluid">
            <span class="navbar-brand fw-bold neon-text">PROHIRE | Panel Empresa</span>
            <div>
                <span class="me-3">Empresa: <strong><%= u.getNombre() %></strong></span>
                <a href="LogoutServlet" class="btn btn-outline-danger btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <% if(request.getParameter("exito") != null) { %>
            <div class="alert alert-success bg-dark text-success border-success text-center fw-bold">
                ¡Acción realizada con éxito!
            </div>
        <% } %>

        <div class="row">
            <!-- Sección Izquierda: Publicar y Ver Vacantes -->
            <div class="col-md-6 mb-4">
                <div class="card card-custom p-4 shadow-sm mb-4">
                    <h4 class="neon-text mb-3">📝 Publicar Nueva Vacante</h4>
                    
                    <form action="VacanteServlet" method="POST">
                        <input type="hidden" name="id_empresa" value="<%= u.getId_usuario() %>">
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="fw-bold text-light form-label">Cargo:</label>
                                <input type="text" name="cargo" class="form-control form-control-sm bg-dark text-white border-secondary" placeholder="Ej. Desarrollador Java" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="fw-bold text-light form-label">Salario:</label>
                                <input type="text" name="salario" class="form-control form-control-sm bg-dark text-white border-secondary" placeholder="Ej. $3.500.000 COP" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label class="fw-bold text-light form-label">Modalidad:</label>
                                <select name="modalidad" class="form-select form-select-sm bg-dark text-white border-secondary">
                                    <option value="Remoto">Remoto</option>
                                    <option value="Presencial">Presencial</option>
                                    <option value="Híbrido">Híbrido</option>
                                </select>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label class="fw-bold text-light form-label">Contrato:</label>
                                <select name="tipo_contrato" class="form-select form-select-sm bg-dark text-white border-secondary">
                                    <option value="Término Indefinido">Término Indefinido</option>
                                    <option value="Término Fijo">Término Fijo</option>
                                    <option value="Prestación de Servicios">Prestación de Servicios</option>
                                </select>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label class="fw-bold text-light form-label">Ubicación:</label>
                                <input type="text" name="ubicacion" class="form-control form-control-sm bg-dark text-white border-secondary" placeholder="Ej. Bogotá" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="fw-bold text-light form-label">Descripción:</label>
                            <textarea name="descripcion" class="form-control form-control-sm bg-dark text-white border-secondary" rows="3" placeholder="Requisitos y funciones del puesto..." required></textarea>
                        </div>

                        <button type="submit" class="btn btn-neon w-100 btn-sm">Publicar Vacante</button>
                    </form>
                </div>

                <!-- Lista de vacantes publicadas -->
                <div class="card card-custom p-3 shadow-sm">
                    <h5 class="neon-text mb-3">Tus Vacantes Publicadas</h5>
                    <table class="table table-sm table-custom table-borderless">
                        <thead>
                            <tr>
                                <th>Cargo</th>
                                <th class="text-end">Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if(misVacantes.isEmpty()) { %>
                                <tr><td colspan="2" class="text-secondary small">No has publicado vacantes aún.</td></tr>
                            <% } else { 
                                for(Vacante v : misVacantes) { %>
                                <tr>
                                    <td><%= v.getCargo() %></td>
                                    <td class="text-end">
                                        <a href="AccionesServlet?accion=borrarVacante&id=<%= v.getId_vacante() %>" class="btn btn-outline-danger btn-sm">Eliminar</a>
                                    </td>
                                </tr>
                            <%  } 
                            } %>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Sección Derecha: Candidatos Postulados -->
            <div class="col-md-6">
                <div class="card card-custom p-4 shadow-sm border-top border-info border-4">
                    <h4 class="neon-text mb-3">👥 Candidatos Postulados</h4>
                    <table class="table table-sm table-custom table-borderless">
                        <thead>
                            <tr>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th class="text-end">Gestión</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if(candidatos.isEmpty()) { %>
                                <tr><td colspan="3" class="text-secondary small">No hay postulantes registrados todavía.</td></tr>
                            <% } else {
                                for(Postulacion p : candidatos) { %>
                                <tr style="border-bottom: 1px solid #333;">
                                    <td style="font-size: 0.85rem;" class="pt-2"><%= p.getFecha_postulacion() %></td>
                                    <td class="pt-2">
                                        <span class="badge <%= p.getEstado().equals("ACEPTADO") ? "bg-success" : p.getEstado().equals("RECHAZADO") ? "bg-danger" : "bg-warning text-dark" %>">
                                            <%= p.getEstado() %>
                                        </span>
                                    </td>
                                    <td class="text-end">
                                        <a href="AccionesServlet?accion=aceptar&id=<%= p.getId_postulacion() %>" class="btn btn-outline-success btn-sm me-1">Aceptar</a>
                                        <a href="AccionesServlet?accion=rechazar&id=<%= p.getId_postulacion() %>" class="btn btn-outline-danger btn-sm">Rechazar</a>
                                    </td>
                                </tr>
                            <%  } 
                            } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>