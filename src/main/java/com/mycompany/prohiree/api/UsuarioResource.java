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
            
            if (dao.actualizarPerfil(u)) {
                return Response.status(Response.Status.OK)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                        .header("Access-Control-Allow-Headers", "content-type")
                        .entity("{\"mensaje\": \"Perfil actualizado exitosamente en la base de datos.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .header("Access-Control-Allow-Origin", "*")
                        .entity("{\"error\": \"No se pudo actualizar el perfil del usuario.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.listarTodos();
        
        // Retornamos siempre estado 200 con la lista (aunque esté vacía []) para evitar errores en JS
        return Response.status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .entity(lista)
                .build();
    }
}