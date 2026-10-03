package com.mycompany.prohiree.api;

import com.prohire.dao.PostulacionDAO;
import com.prohire.model.Postulacion;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/postulaciones")
public class PostulacionResource {

    @POST
    @Path("/apply")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aplicarVacante(Map<String, String> datos) {
        try {
            PostulacionDAO dao = new PostulacionDAO();
            int idProfesional = Integer.parseInt(datos.get("id_profesional"));
            int idVacante = Integer.parseInt(datos.get("id_vacante"));

            if (dao.verificarPostulacion(idProfesional, idVacante)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"error\": \"Ya te has postulado a esta vacante anteriormente.\"}")
                        .build();
            }

            Postulacion p = new Postulacion();
            p.setId_profesional(idProfesional);
            p.setId_vacante(idVacante);

            if (dao.registrarPostulacion(p)) {
                return Response.status(Response.Status.CREATED)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"mensaje\": \"Postulación enviada con éxito.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"error\": \"No se pudo registrar la postulación.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/profesional/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorProfesional(@PathParam("id") int idProfesional) {
        try {
            PostulacionDAO dao = new PostulacionDAO();
            List<Postulacion> lista = dao.listarPorProfesional(idProfesional);
            
            if (lista != null && !lista.isEmpty()) {
                return Response.status(Response.Status.OK)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(lista).build();
            }
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("[]").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/empresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verPostulacionesPorEmpresa(@PathParam("idEmpresa") int idEmpresa) {
        try {
            PostulacionDAO dao = new PostulacionDAO();
            List<Postulacion> lista = dao.listarPorEmpresa(idEmpresa);
            
            if (lista != null && !lista.isEmpty()) {
                return Response.status(Response.Status.OK)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(lista).build();
            }
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("[]").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/updateState")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEstado(Map<String, String> datos) {
        try {
            PostulacionDAO dao = new PostulacionDAO();
            int idPostulacion = Integer.parseInt(datos.get("id_postulacion"));
            String estado = datos.get("estado");

            if (dao.actualizarEstado(idPostulacion, estado)) {
                return Response.status(Response.Status.OK)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"mensaje\": \"Estado actualizado a: " + estado + "\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("{\"error\": \"Error al actualizar el estado.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}