package org.ksij.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.calendrier.Calendrier;

import java.util.List;

@Path("calendrier")
public class CalendrierController {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Calendrier> getAllCalendrier() {
        return Calendrier.listAll();
    }

    @GET
    @Path("one/{dateTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCalendrier(@PathParam("dateTime") String dateTime) {
        Calendrier calendrier = Calendrier.find("dateTime",dateTime).firstResult();
        if(calendrier == null){
            return  Response.status(400).build();
        }else{
            //System.out.println(calendrier.toString());
            return  Response.ok(calendrier).build();
        }
    }

    @PUT
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveAndPutCalendrier(Calendrier calendrier) {
        //
        if(calendrier.id == null){
            calendrier.persist();
            return Response.ok().build();
        }else{
            Calendrier calendrier1 = Calendrier.findById(calendrier.id);
            if(calendrier1 != null){
                //
                //calendrier1.titre = calendrier.titre;
                calendrier1.contenu = calendrier.contenu;
                calendrier1.dateTime = calendrier.dateTime;
                calendrier1.dateTimeLunaire = calendrier.dateTimeLunaire;
                //calendrier1.heure = calendrier.heure;
                calendrier1.couleur = calendrier.couleur;
                System.out.println(calendrier.couleur);
                //
                return Response.ok().build();
            }else{
                return Response.serverError().build();
            }

        }
        //
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response supprimerCal(@PathParam("id") Long id) {
        Calendrier.deleteById(id);
        return  Response.ok().build();
    }

}
