package com.prohire.controller;

import com.prohire.dao.PostulacionDAO;
import com.prohire.model.Postulacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostulacionServlet", urlPatterns = {"/PostulacionServlet"})
public class PostulacionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 1. Recibir los datos
            int idProfesional = Integer.parseInt(request.getParameter("id_profesional"));
            int idVacante = Integer.parseInt(request.getParameter("id_vacante"));
            
            PostulacionDAO dao = new PostulacionDAO();
            
            // 2. VALIDACIÓN: Verificamos si ya existe la postulación
            if (dao.verificarPostulacion(idProfesional, idVacante)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"status\": \"error\", \"mensaje\": \"Ya te has postulado a esta vacante.\"}");
                return; 
            }
            
            // 3. Si no existe, registramos la postulación
            Postulacion p = new Postulacion();
            p.setId_profesional(idProfesional);
            p.setId_vacante(idVacante);
            
            if (dao.registrarPostulacion(p)) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("{\"status\": \"success\", \"mensaje\": \"Postulación exitosa.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"status\": \"error\", \"mensaje\": \"Error al guardar en la base de datos.\"}");
            }
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"status\": \"error\", \"mensaje\": \"Datos inválidos: " + e.getMessage() + "\"}");
        }
    }
}