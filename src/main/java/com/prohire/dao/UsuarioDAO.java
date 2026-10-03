package com.prohire.dao;

import com.prohire.model.Usuario;
import com.prohire.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // 1. Método para verificar si un email ya existe
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar email: " + e.getMessage());
        }
        return false;
    }

    // 2. Método para REGISTRAR o INSERTAR (Create) con validación de duplicados
    public boolean registrar(Usuario u) {
        // Evita que se creen cuentas con el mismo correo
        if (existeEmail(u.getEmail())) {
            System.err.println("❌ Error: El correo ya está registrado.");
            return false;
        }

        String sql = "INSERT INTO usuarios (nombre, email, password, rol, profesion, telefono, ruta_cv, cv_documento, pin_seguridad, foto_perfil) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRol());
            ps.setString(5, u.getProfesion());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getRuta_cv() != null ? u.getRuta_cv() : "");
            ps.setString(8, u.getCv_documento() != null ? u.getCv_documento() : "");
            ps.setString(9, u.getPin_seguridad());
            ps.setString(10, u.getFoto_perfil() != null ? u.getFoto_perfil() : "");

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Usuario (Registro): " + e.getMessage());
            e.printStackTrace(); 
            return false;
        }
    }

    // 3. Método para VALIDAR EL LOGIN (Read)
    public Usuario validar(String email, String pass) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setRol(rs.getString("rol"));
                u.setProfesion(rs.getString("profesion"));
                u.setTelefono(rs.getString("telefono")); 
                u.setRuta_cv(rs.getString("ruta_cv"));
                u.setPin_seguridad(rs.getString("pin_seguridad"));
                u.setFoto_perfil(rs.getString("foto_perfil"));     
                u.setCv_documento(rs.getString("cv_documento"));    
                return u;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error Login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // 4. Método para ACTUALIZAR EL PERFIL (Actualizado para incluir nombre, PIN y proteger contraseña vacía)
    public boolean actualizarPerfil(Usuario u) {
        boolean cambiarPassword = (u.getPassword() != null && !u.getPassword().trim().isEmpty());
        
        String sql;
        if (cambiarPassword) {
            // Si el usuario escribió una contraseña nueva, la actualizamos junto con el nombre, email, profesion, telefono, pin y archivos
            sql = "UPDATE usuarios SET nombre = ?, email = ?, password = ?, profesion = ?, telefono = ?, pin_seguridad = ?, ruta_cv = ?, cv_documento = ?, foto_perfil = ? WHERE id_usuario = ?";
        } else {
            // Si la dejó en blanco, mantenemos la contraseña anterior intacta y actualizamos el resto
            sql = "UPDATE usuarios SET nombre = ?, email = ?, profesion = ?, telefono = ?, pin_seguridad = ?, ruta_cv = ?, cv_documento = ?, foto_perfil = ? WHERE id_usuario = ?";
        }

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            int index = 1;
            ps.setString(index++, u.getNombre());
            ps.setString(index++, u.getEmail());
            
            if (cambiarPassword) {
                ps.setString(index++, u.getPassword());
            }
            
            ps.setString(index++, u.getProfesion());
            ps.setString(index++, u.getTelefono());
            ps.setString(index++, u.getPin_seguridad());
            ps.setString(index++, u.getRuta_cv() != null ? u.getRuta_cv() : "");
            ps.setString(index++, u.getCv_documento() != null ? u.getCv_documento() : "");
            ps.setString(index++, u.getFoto_perfil() != null ? u.getFoto_perfil() : "");
            ps.setInt(index, u.getId_usuario());

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Usuario (Actualizar Perfil): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 5. Método para ELIMINAR (Delete)
    public boolean eliminar(int id_usuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id_usuario);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Usuario (Eliminar): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 6. Método para LISTAR TODOS (Read / GET)
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setRol(rs.getString("rol"));
                u.setProfesion(rs.getString("profesion"));
                u.setTelefono(rs.getString("telefono"));
                u.setRuta_cv(rs.getString("ruta_cv"));
                u.setPin_seguridad(rs.getString("pin_seguridad"));
                u.setFoto_perfil(rs.getString("foto_perfil"));     
                u.setCv_documento(rs.getString("cv_documento"));    
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error DAO Usuario (Listar): " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}