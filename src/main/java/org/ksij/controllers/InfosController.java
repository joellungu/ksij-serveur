package org.ksij.controllers;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.EvenNotification;
import org.ksij.models.Token;
import org.ksij.models.evenements.Evenement;
import org.ksij.models.infos.Infos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Path("infos")
public class InfosController {

    @Inject
    EvenNotification evenNotification;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Infos> getAllEvenement() {
        List<Infos> infos = Infos.listAll();
        infos.forEach((e)->{
            //
            e.photo = new byte[0];
        });
        return infos;
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Infos infos){
        infos.persist();
        //
        List<Token> tokens = Token.listAll();
        //
        try {
            HashMap<String, String> e = new HashMap<>();
            e.put("id", ""+infos.id);
            e.put("titre", ""+infos.titre);
            e.put("contenu", ""+infos.contenu);
            e.put("auteur", ""+infos.auteur);
            e.put("dateTime", ""+infos.dateTime);
            e.put("sousTitre", ""+infos.sousTitre);
            e.put("asPhoto", ""+infos.asPhoto);
            //e.put("id", ""+evenement.);


            evenNotification.verification(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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

    @GET
    @Path("photo/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getPhotoEvenement(@PathParam("id") Long id) {
        Infos e = Infos.findById(id);
        return e.photo;
    }

}
