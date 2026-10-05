package com.prohire.dao;

import com.prohire.model.Vacante;
import com.prohire.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VacanteDAO {

    // 1. Crear Vacante
    public boolean registrar(Vacante v) {
        String sql = "INSERT INTO vacantes (id_empresa, cargo, salario, modalidad, tipo_contrato, ubicacion, descripcion) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, v.getId_empresa());
            ps.setString(2, v.getCargo());
            ps.setString(3, v.getSalario());
            ps.setString(4, v.getModalidad());
            ps.setString(5, v.getTipo_contrato());
            ps.setString(6, v.getUbicacion());
            ps.setString(7, v.getDescripcion());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Vacante (Registro): " + e.getMessage());
            return false;
        }
    }

    // 2. Listar todas las vacantes (Servicio principal)
    public List<Vacante> listarTodas() {
        List<Vacante> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacantes";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Vacante v = new Vacante();
                v.setId_vacante(rs.getInt("id_vacante"));
                v.setId_empresa(rs.getInt("id_empresa"));
                v.setCargo(rs.getString("cargo"));
                v.setSalario(rs.getString("salario"));
                v.setModalidad(rs.getString("modalidad"));
                v.setTipo_contrato(rs.getString("tipo_contrato"));
                v.setUbicacion(rs.getString("ubicacion"));
                v.setDescripcion(rs.getString("descripcion"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Vacante (Listar): " + e.getMessage());
        }
        return lista;
    }

    // 💡 ALIAS: Redirige listar() a listarTodas() para compatibilidad con el JSP profesional
    public List<Vacante> listar() {
        return listarTodas();
    }

    // 3. Listar vacantes específicas por ID de empresa
    public List<Vacante> listarPorEmpresa(int id_empresa) {
        List<Vacante> lista = new ArrayList<>();
        String sql = "SELECT * FROM vacantes WHERE id_empresa = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id_empresa);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vacante v = new Vacante();
                    v.setId_vacante(rs.getInt("id_vacante"));
                    v.setId_empresa(rs.getInt("id_empresa"));
                    v.setCargo(rs.getString("cargo"));
                    v.setSalario(rs.getString("salario"));
                    v.setModalidad(rs.getString("modalidad"));
                    v.setTipo_contrato(rs.getString("tipo_contrato"));
                    v.setUbicacion(rs.getString("ubicacion"));
                    v.setDescripcion(rs.getString("descripcion"));
                    lista.add(v);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Vacante (Listar por Empresa): " + e.getMessage());
        }
        return lista;
    }

    // 4. Eliminar Vacante (Con borrado en cascada manual transaccional para limpiar postulaciones)
    public boolean eliminar(int id_vacante) {
        String sqlPostulaciones = "DELETE FROM postulaciones WHERE id_vacante = ?";
        String sqlVacante = "DELETE FROM vacantes WHERE id_vacante = ?";
        
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false); // Iniciamos transacción

            // Paso 1: Eliminar las postulaciones asociadas a esta vacante
            try (PreparedStatement psPost = con.prepareStatement(sqlPostulaciones)) {
                psPost.setInt(1, id_vacante);
                psPost.executeUpdate();
            }

            // Paso 2: Eliminar la vacante
            int filasAfectadas = 0;
            try (PreparedStatement psVac = con.prepareStatement(sqlVacante)) {
                psVac.setInt(1, id_vacante);
                filasAfectadas = psVac.executeUpdate();
            }

            con.commit(); // Confirmamos los cambios si todo salió bien
            return filasAfectadas > 0;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Si ocurre un error, revertimos todo
                } catch (SQLException ex) {
                    System.err.println("❌ Error en rollback: " + ex.getMessage());
                }
            }
            System.err.println("❌ Error DAO Vacante (Eliminar): " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}