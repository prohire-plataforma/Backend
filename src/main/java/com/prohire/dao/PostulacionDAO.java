package com.prohire.dao;

import com.prohire.model.Postulacion;
import com.prohire.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostulacionDAO {

    /**
     * Módulo: Inserción (Create)
     */
    public boolean registrarPostulacion(Postulacion p) {
        String sql = "INSERT INTO postulaciones(id_profesional, id_vacante, estado) VALUES (?, ?, 'ENVIADA')";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, p.getId_profesional());
            ps.setInt(2, p.getId_vacante());
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Postulacion (Registro): " + e.getMessage());
            return false;
        }
    }

    /**
     * Módulo: Validación de Lógica de Negocio
     */
    public boolean verificarPostulacion(int idProfesional, int idVacante) {
        String sql = "SELECT COUNT(*) FROM postulaciones WHERE id_profesional = ? AND id_vacante = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProfesional);
            ps.setInt(2, idVacante);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Postulacion (Verificar): " + e.getMessage());
        }
        return false;
    }

    /**
     * Módulo: Consulta por Empresa (Read) - CON JOIN PARA TRAER DATOS DEL PROFESIONAL
     */
    public List<Postulacion> listarPorEmpresa(int idEmpresa) {
        List<Postulacion> lista = new ArrayList<>();
        String sql = "SELECT p.id_postulacion, p.id_profesional, p.id_vacante, p.estado, p.fecha_postulacion, " +
                     "u.nombre AS nombreProfesional, u.profesion AS profesionProfesional, " +
                     "u.telefono AS telefonoProfesional, u.email AS emailProfesional, u.cv_documento AS cvProfesional " +
                     "FROM postulaciones p " +
                     "INNER JOIN vacantes v ON p.id_vacante = v.id_vacante " +
                     "INNER JOIN usuarios u ON p.id_profesional = u.id_usuario " +
                     "WHERE v.id_empresa = ?";
                     
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idEmpresa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Postulacion p = new Postulacion();
                    p.setId_postulacion(rs.getInt("id_postulacion"));
                    p.setId_profesional(rs.getInt("id_profesional"));
                    p.setId_vacante(rs.getInt("id_vacante"));
                    p.setEstado(rs.getString("estado"));
                    p.setFecha_postulacion(rs.getString("fecha_postulacion")); 
                    
                    // Asignamos los datos del profesional obtenidos mediante el JOIN
                    p.setNombreProfesional(rs.getString("nombreProfesional"));
                    p.setProfesionProfesional(rs.getString("profesionProfesional"));
                    p.setTelefonoProfesional(rs.getString("telefonoProfesional"));
                    p.setEmailProfesional(rs.getString("emailProfesional"));
                    p.setCvProfesional(rs.getString("cvProfesional"));
                    
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Postulacion (Listar Empresa): " + e.getMessage());
        }
        return lista;
    }

    /**
     * Módulo: Consulta por Profesional (Read)
     */
    public List<Postulacion> listarPorProfesional(int idProfesional) {
        List<Postulacion> lista = new ArrayList<>();
        String sql = "SELECT id_postulacion, id_profesional, id_vacante, estado, fecha_postulacion " +
                     "FROM postulaciones WHERE id_profesional = ?";
                     
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProfesional);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Postulacion p = new Postulacion();
                    p.setId_postulacion(rs.getInt("id_postulacion"));
                    p.setId_profesional(rs.getInt("id_profesional"));
                    p.setId_vacante(rs.getInt("id_vacante"));
                    p.setEstado(rs.getString("estado"));
                    p.setFecha_postulacion(rs.getString("fecha_postulacion"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Postulacion (Listar Profesional): " + e.getMessage());
        }
        return lista;
    }

    /**
     * Módulo: Actualización (Update)
     */
    public boolean actualizarEstado(int idPostulacion, String estado) {
        String sql = "UPDATE postulaciones SET estado = ? WHERE id_postulacion = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, estado);
            ps.setInt(2, idPostulacion);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Postulacion (Actualizar Estado): " + e.getMessage());
            return false;
        }
    }
}