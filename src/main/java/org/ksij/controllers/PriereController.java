package org.ksij.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.calendrier.Calendrier;
import org.ksij.models.infos.Infos;
import org.ksij.models.priere.Priere;

import java.util.List;

@Path("priere")
public class PriereController {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Priere> getAllEvenement() {
        return Priere.listAll();
    }

    @GET
    @Path("one/{dateTime}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCalendrier(@PathParam("dateTime") String dateTime) {
        Priere priere = Priere.find("dateTime",dateTime).firstResult();
        if(priere == null){
            return  Response.status(400).build();
        }else{
            //System.out.println(calendrier.toString());
            return  Response.ok(priere).build();
        }
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Priere priere){
        priere.persist();
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveAndPutCalendrier(Priere priere) {
        //
        if(priere.id == null){
            priere.persist();
            return Response.ok().build();
        }else{
            Priere priere1 = Priere.findById(priere.id);
            if(priere1 != null){
                //
                //calendrier1.titre = calendrier.titre;
                priere1.dateTime = priere.dateTime;
                priere1.horaires = priere.horaires;
                System.out.println(priere.dateTime);
                //
                return Response.ok().build();
            }else{
                return Response.serverError().build();
            }

        }
        //
    }

    @DELETE
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEvenement(Priere priere){
        boolean r =  Priere.deleteById(priere.id);
        if(!r){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}
