<%@page import="com.prohire.model.Usuario"%>
<%@page import="com.prohire.model.Vacante"%>
<%@page import="com.prohire.model.Postulacion"%>
<%@page import="com.prohire.dao.VacanteDAO"%>
<%@page import="com.prohire.dao.PostulacionDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Verificamos sesión y rol de profesional
    Usuario u = (Usuario) session.getAttribute("userLog");
    if (u == null || !u.getRol().equals("profesional")) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Instancias de DAOs y consulta de datos
    VacanteDAO vDao = new VacanteDAO();
    List<Vacante> listaVacantes = vDao.listar();

    PostulacionDAO pDao = new PostulacionDAO();
    List<Postulacion> misPostulaciones = pDao.listarPorProfesional(u.getId_usuario());
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PROHIRE - Panel Profesional</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #121212; color: white; }
        .navbar-custom { background-color: #0d0d0d; border-bottom: 2px solid #00e5ff; }
        .neon-text { color: #00e5ff; text-shadow: 0 0 5px rgba(0, 229, 255, 0.5); }
        .card-custom { background-color: #1e1e1e; border: 1px solid #333; transition: transform 0.2s; }
        .card-custom:hover { border-color: #00e5ff; transform: translateY(-3px); box-shadow: 0 5px 15px rgba(0, 229, 255, 0.2); }
        .btn-neon { background-color: transparent; border: 1px solid #00e5ff; color: #00e5ff; font-weight: bold; }
        .btn-neon:hover { background-color: #00e5ff; color: #121212; box-shadow: 0 0 10px #00e5ff; }
        .table-custom { color: white; }
        .table-custom th { color: #00e5ff; }
    </style>
</head>
<body>

    <nav class="navbar navbar-custom p-3 shadow mb-4">
        <div class="container-fluid">
            <span class="navbar-brand fw-bold neon-text">PROHIRE | Panel Profesional</span>
            <div>
                <span class="me-3">¡Hola, <strong><%= u.getNombre() %></strong>! 👋</span>
                <a href="LogoutServlet" class="btn btn-outline-danger btn-sm">Cerrar Sesión</a>
            </div>
        </div>
    </nav>

    <div class="container mb-5">
        
        <!-- SECCIÓN DE ALERTAS INTELIGENTES -->
        <% if(request.getParameter("exito") != null) { %>
            <div class="alert alert-success bg-dark text-success border-success text-center fw-bold">
                ¡Postulación enviada con éxito! La empresa la revisará pronto.
            </div>
        <% } %>
        
        <% if(request.getParameter("error") != null) { 
            String tipoError = request.getParameter("error");
            if(tipoError.equals("duplicado")) {
        %>
            <div class="alert alert-warning bg-dark text-warning border-warning text-center fw-bold">
                ⚠️ Ya te has postulado a esta vacante anteriormente.
            </div>
        <%  } else { %>
            <div class="alert alert-danger bg-dark text-danger border-danger text-center fw-bold">
                Ocurrió un error al procesar tu postulación. Intenta más tarde.
            </div>
        <%  }
        } %>
        <!-- FIN DE ALERTAS -->

        <!-- SECCIÓN DE MIS POSTULACIONES Y ESTADOS -->
        <div class="card card-custom p-4 shadow-sm mb-5 border-top border-info border-3">
            <h4 class="neon-text mb-3">📋 Estado de mis Postulaciones</h4>
            <div class="table-responsive">
                <table class="table table-sm table-custom table-borderless align-middle mb-0">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Estado del Proceso</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(misPostulaciones.isEmpty()) { %>
                            <tr>
                                <td colspan="2" class="text-secondary small py-3">Aún no te has postulado a ninguna vacante.</td>
                            </tr>
                        <% } else {
                            for(Postulacion p : misPostulaciones) { 
                                String estado = (p.getEstado() != null) ? p.getEstado().toUpperCase() : "PENDIENTE";
                        %>
                            <tr style="border-bottom: 1px solid #2a2a2a;">
                                <td style="font-size: 0.9rem;" class="py-3"><%= p.getFecha_postulacion() %></td>
                                <td class="py-3">
                                    <% if (estado.equals("ACEPTADO")) { %>
                                        <span class="badge bg-success p-2">🎉 ¡ACEPTADO! La empresa revisará tu perfil</span>
                                    <% } else if (estado.equals("RECHAZADO")) { %>
                                        <span class="badge bg-danger p-2">❌ RECHAZADO / PROCESO CERRADO</span>
                                    <% } else if (estado.equals("ELIMINADO") || estado.equals("CANCELADO")) { %>
                                        <span class="badge bg-secondary p-2">⚠️ Vacante eliminada por la empresa</span>
                                    <% } else { %>
                                        <span class="badge bg-warning text-dark p-2">⏳ EN REVISIÓN</span>
                                    <% } %>
                                </td>
                            </tr>
                        <%  } 
                        } %>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- SECCIÓN DE VACANTES DISPONIBLES -->
        <h3 class="mb-4 neon-text border-bottom border-secondary pb-2">🚀 Vacantes Disponibles en Tiempo Real</h3>
        
        <div class="row">
            <% 
                if(listaVacantes.isEmpty()) {
            %>
                <div class="col-12 text-center text-secondary mt-4">
                    <h5>No hay vacantes publicadas en este momento. Vuelve más tarde.</h5>
                </div>
            <%  } else {
                    for(Vacante v : listaVacantes) {
            %>
            <div class="col-md-4 mb-4">
                <div class="card card-custom p-4 h-100 shadow-sm d-flex flex-column">
                    <h5 class="neon-text fw-bold mb-1"><%= v.getCargo() %></h5>
                    <p class="text-secondary small mb-3">📍 <%= v.getUbicacion() %> | 💼 <%= v.getModalidad() %></p>
                    
                    <h6 class="text-light mb-3">💰 <%= v.getSalario() %></h6>
                    
                    <form action="PostulacionServlet" method="POST" class="mt-auto pt-2">
                        <input type="hidden" name="id_profesional" value="<%= u.getId_usuario() %>">
                        <input type="hidden" name="id_vacante" value="<%= v.getId_vacante() %>">
                        <button type="submit" class="btn btn-neon w-100">Enviar Postulación</button>
                    </form>
                </div>
            </div>
            <%      } 
                } %>
        </div>

    </div>
</body>
</html>