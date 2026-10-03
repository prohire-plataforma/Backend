package com.prohire.controller;

import com.prohire.dao.VacanteDAO;
import com.prohire.model.Vacante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VacanteServlet", urlPatterns = {"/VacanteServlet"})
public class VacanteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Recibir los datos del formulario del dashboard
        Vacante v = new Vacante();
        v.setId_empresa(Integer.parseInt(request.getParameter("id_empresa")));
        v.setCargo(request.getParameter("cargo"));
        v.setSalario(request.getParameter("salario"));
        v.setModalidad(request.getParameter("modalidad"));
        v.setTipo_contrato(request.getParameter("tipo_contrato"));
        v.setUbicacion(request.getParameter("ubicacion"));
        v.setDescripcion(request.getParameter("descripcion"));
        
        // 2. Ejecutar la inserción en la base de datos
        VacanteDAO dao = new VacanteDAO();
        
        // 3. Validar el resultado y redirigir
        if (dao.registrar(v)) {
            // ÉXITO: Redirige al dashboard de la empresa enviando la señal de éxito
            response.sendRedirect("dashboard_empresa.jsp?exito=1");
        } else {
            // ERROR: Redirige de vuelta con un mensaje de error por si falla la base de datos
            response.sendRedirect("dashboard_empresa.jsp?error=1");
        }
    }
}