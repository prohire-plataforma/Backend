package com.prohire.controller;

import com.prohire.dao.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "AccionesServlet", urlPatterns = {"/AccionesServlet"})
public class AccionesServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        int id = Integer.parseInt(request.getParameter("id"));

        if (null != accion) switch (accion) {
            case "borrarVacante" -> new VacanteDAO().eliminar(id);
            case "aceptar" -> new PostulacionDAO().actualizarEstado(id, "ACEPTADO");
            case "rechazar" -> new PostulacionDAO().actualizarEstado(id, "RECHAZADO");
            default -> {
            }
        }
        response.sendRedirect("dashboard_empresa.jsp");
    }
}