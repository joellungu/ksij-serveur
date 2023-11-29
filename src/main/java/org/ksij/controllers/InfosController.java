package org.ksij.controllers;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.evenements.Evenement;
import org.ksij.models.infos.Infos;

import java.util.List;

@Path("infos")
public class InfosController {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Infos> getAllEvenement() {
        return Infos.listAll();
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Infos infos){
        infos.persist();
        return Response.ok().build();
    }

    @PUT
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putEvenement(Infos infos){
        Infos infos1 = Infos.findById(infos.id);
        if(infos1 == null){
            return Response.serverError().build();
        }

        infos1.titre = infos.titre;
        infos1.contenu = infos.contenu;
        infos1.auteur = infos.auteur;
        infos1.dateTime = infos.dateTime;

        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEvenement(@PathParam("id") Long id){
        boolean r =  Infos.deleteById(id);
        if(!r){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}
