package com.mycompany.prohiree.api;

import com.prohire.dao.UsuarioDAO;
import com.prohire.model.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
public class UsuarioResource {

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPerfil(Usuario u) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            
            if (u.getId_usuario() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"error\": \"ID de usuario inválido para actualizar.\"}")
                        .build();
            }

            boolean actualizado = dao.actualizarPerfil(u);

            if (actualizado) {
                return Response.status(Response.Status.OK)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"mensaje\": \"Perfil y PIN actualizados exitosamente en la base de datos.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"error\": \"No se pudo actualizar el perfil del usuario.\"}")
                        .build();
            }
        } catch (Exception e) {
            System.err.println("❌ Error en UsuarioResource /update: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> lista = dao.listarTodos();
            
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(lista)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}