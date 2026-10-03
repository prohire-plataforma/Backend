package com.mycompany.prohiree.api;

import com.prohire.dao.UsuarioDAO;
import com.prohire.model.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest credenciales) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuarioValidado = dao.validar(credenciales.getEmail(), credenciales.getPassword());

            if (usuarioValidado != null) {
                return Response.status(Response.Status.OK)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(usuarioValidado)
                        .build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"error\": \"Credenciales inválidas\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"Error en servidor: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registro(Usuario nuevoUsuario) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            
            if (dao.registrar(nuevoUsuario)) {
                return Response.status(Response.Status.CREATED)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"mensaje\": \"Registro exitoso\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"error\": \"No se pudo registrar el usuario. El correo podría ya existir.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"Error en servidor: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}