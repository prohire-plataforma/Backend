package com.mycompany.prohiree.api;

import com.prohire.dao.VacanteDAO;
import com.prohire.model.Vacante;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/vacantes")
public class VacanteResource {

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearVacante(Map<String, String> datos) {
        try {
            VacanteDAO dao = new VacanteDAO();
            Vacante v = new Vacante();
            
            // Asignamos los datos usando el modelo real
            v.setId_empresa(Integer.parseInt(datos.get("id_empresa")));
            v.setCargo(datos.get("cargo"));
            v.setSalario(datos.getOrDefault("salario", "A convenir"));
            v.setModalidad(datos.getOrDefault("modalidad", "Presencial"));
            v.setTipo_contrato(datos.getOrDefault("tipo_contrato", "Término Indefinido"));
            v.setUbicacion(datos.getOrDefault("ubicacion", "No especificada"));
            v.setDescripcion(datos.get("descripcion"));

            if (dao.registrar(v)) {
                return Response.status(Response.Status.CREATED)
                               .entity("{\"mensaje\": \"Vacante publicada con éxito\"}")
                               .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("{\"error\": \"Error al crear vacante. Verifica la base de datos.\"}")
                               .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarVacantes() {
        VacanteDAO dao = new VacanteDAO();
        List<Vacante> lista = dao.listarTodas();
        
        if (!lista.isEmpty()) {
            return Response.status(Response.Status.OK)
                           .entity(lista)
                           .build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarVacante(@PathParam("id") int id_vacante) {
        VacanteDAO dao = new VacanteDAO();
        
        if (dao.eliminar(id_vacante)) {
            return Response.status(Response.Status.OK)
                           .entity("{\"mensaje\": \"Vacante eliminada\"}")
                           .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity("{\"error\": \"Error al eliminar. Verifica que el ID exista.\"}")
                       .build();
    }
}