package org.ksij.controllers;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.evenements.Evenement;

import java.util.List;


@Path("evenement")
public class EvenementController {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Evenement> getAllEvenement() {
        return Evenement.listAll();
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Evenement evenement){
        evenement.persist();
        return Response.ok().build();
    }

    @PUT
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putEvenement(Evenement evenement){
        Evenement evenement1 = Evenement.findById(evenement.id);
        if(evenement1 == null){
            return Response.serverError().build();
        }

        evenement1.titre = evenement.titre;
        evenement1.contenu = evenement.contenu;
        evenement1.auteur = evenement.auteur;
        evenement1.dateTime = evenement.dateTime;

        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEvenement(@PathParam("id") Long id){
        boolean r =  Evenement.deleteById(id);
        if(!r){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("photo/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getPhotoEvenement(@PathParam("id") Long id) {
        Evenement e = Evenement.findById(id);
        return e.photo;
    }

}
