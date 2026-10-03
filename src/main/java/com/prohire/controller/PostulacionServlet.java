package com.prohire.controller;

import com.prohire.dao.PostulacionDAO;
import com.prohire.model.Postulacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PostulacionServlet", urlPatterns = {"/PostulacionServlet"})
public class PostulacionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Recibir los datos
        int idProfesional = Integer.parseInt(request.getParameter("id_profesional"));
        int idVacante = Integer.parseInt(request.getParameter("id_vacante"));
        
        PostulacionDAO dao = new PostulacionDAO();
        
        // 2. VALIDACIÓN: Verificamos si ya existe la postulación
        if (dao.verificarPostulacion(idProfesional, idVacante)) {
            // Ya existe, devolvemos al usuario con alerta amarilla
            response.sendRedirect("dashboard_profesional.jsp?error=duplicado");
            return; 
        }
        
        // 3. Si no existe, registramos la postulación
        Postulacion p = new Postulacion();
        p.setId_profesional(idProfesional);
        p.setId_vacante(idVacante);
        
        if (dao.registrarPostulacion(p)) {
            // ÉXITO
            response.sendRedirect("dashboard_profesional.jsp?exito=1");
        } else {
            // ERROR DE BASE DE DATOS
            response.sendRedirect("dashboard_profesional.jsp?error=1");
        }
    }
}