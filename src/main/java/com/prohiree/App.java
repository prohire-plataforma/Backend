package com.prohiree; // Asegúrate de que este sea el nombre real de tu paquete

import com.prohire.dao.UsuarioDAO;
import com.prohire.model.Usuario;
import com.prohire.util.Conexion;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        // Usar try-with-resources quita la alerta porque cierra la conexión sola
        try (Connection con = Conexion.getConnection()) {
            if (con != null) {
                System.out.println("✅ Conexión establecida con PROHIRE_DB");
                
                Usuario u = new Usuario();
                u.setNombre("Prueba Final");
                // Le cambié el correo para que no te dé error de "correo ya existe" en SQL
                u.setEmail("prueba2@prohire.com"); 
                u.setPassword("sena2026");
                u.setRol("profesional");
                u.setProfesion("Desarrollador Backend");
                u.setTelefono("3150001122");

                UsuarioDAO dao = new UsuarioDAO();
                if (dao.registrar(u)) {
                    System.out.println("✅ Usuario registrado exitosamente en MySQL.");
                } else {
                    System.out.println("❌ No se pudo registrar el usuario.");
                }
            } else {
                System.out.println("❌ Error crítico: No hay conexión.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}