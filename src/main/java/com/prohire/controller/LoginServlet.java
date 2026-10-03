package com.prohire.controller;

import com.prohire.dao.UsuarioDAO;
import com.prohire.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        Usuario u = new UsuarioDAO().validar(email, pass);

        if (u != null) {
            // Guardamos el objeto usuario en la sesión
            HttpSession session = request.getSession();
            session.setAttribute("userLog", u);
            
            // Redirigir según el rol
            if (u.getRol().equals("profesional")) {
                response.sendRedirect("dashboard_profesional.jsp");
            } else {
                response.sendRedirect("dashboard_empresa.jsp");
            }
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
}