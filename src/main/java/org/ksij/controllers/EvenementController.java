package org.ksij.controllers;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.EvenNotification;
import org.ksij.models.Token;
import org.ksij.models.evenements.Evenement;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Path("evenement")
public class EvenementController {

    @Inject
    EvenNotification evenNotification;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Evenement> getAllEvenement() {
        List<Evenement> events = Evenement.listAll();
        events.forEach((e)->{
            //
            e.photo = new byte[0];
        });
        return events;
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Evenement evenement){
        evenement.persist();
        //

        //
        List<Token> tokens = Token.listAll();
        //
        try {
            HashMap<String, String> e = new HashMap<>();
            e.put("id", ""+evenement.id);
            e.put("titre", ""+evenement.titre);
            e.put("contenu", ""+evenement.contenu);
            e.put("auteur", ""+evenement.auteur);
            e.put("dateTime", ""+evenement.dateTime);
            e.put("sousTitre", ""+evenement.sousTitre);
            e.put("asPhoto", ""+evenement.asPhoto);
            //e.put("id", ""+evenement.);


            evenNotification.verification(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //
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

    @GET
    @Path("test/notification")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void getPhotoEvenement() {
        //Android
        //dC5ChNIlT0CL7ol5yl5k2a:APA91bG8wgZSj5kQThphQlJFh-nyQhAiROZRuu-DvJYwBN5wVRFt_7IP98fGOttrRKw62qc369xxnYqR0pikjSE865DICkY-g4PjL2Bd2rzcE7ZfZyV1jVPc7b2wC9kbLDFv66QF5oDq
        String tokenAndroid = "dC5ChNIlT0CL7ol5yl5k2a:APA91bG8wgZSj5kQThphQlJFh-nyQhAiROZRuu-DvJYwBN5wVRFt_7IP98fGOttrRKw62qc369xxnYqR0pikjSE865DICkY-g4PjL2Bd2rzcE7ZfZyV1jVPc7b2wC9kbLDFv66QF5oDq";
        //iOS
        //
        //emGr8h5qL0bpgtk7Fuc5qT:APA91bFsEWqeh1rAOnQMWW8q3wRT7Frw7n2jB7ICEZH_PKrlGbfhUNVay0fiR59srs2gI3_wSxjIrC250CoMeZYc_yFFJkxyrFhs93eoE6cPA35rR9b9jG_nBPZ0vB-xqw1loAkwy4nL
        //
        //fWqY_JYntEqQsk0vbRVX_F:APA91bEdHc6Uu74dSCPEiWIey0DQaQqUK7q3tb6OtVPnwq7rbpwHOpGoWmIrKkB-AqXDBWJNtt7u_HLWvm87AAkOxCobuH5XieQEMaHi_v6JqLAUt1B3ZfnFLv5wRCIRumzSo2FksiIe
        String tokenIos = "emGr8h5qL0bpgtk7Fuc5qT:APA91bFsEWqeh1rAOnQMWW8q3wRT7Frw7n2jB7ICEZH_PKrlGbfhUNVay0fiR59srs2gI3_wSxjIrC250CoMeZYc_yFFJkxyrFhs93eoE6cPA35rR9b9jG_nBPZ0vB-xqw1loAkwy4nL";
        try {
            HashMap<String, String> e = new HashMap<>();
            e.put("id", "");
            e.put("titre", "titre");
            e.put("contenu", "contenu");
            e.put("auteur", "auteur");
            e.put("dateTime", "dateTime");
            e.put("sousTitre", "sousTitre");
            e.put("asPhoto", "asPhoto");
            //
            evenNotification.verification(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * fWqY_JYntEqQsk0vbRVX_F:APA91bEdHc6Uu74dSCPEiWIey0DQaQqUK7q3tb6OtVPnwq7rbpwHOpGoWmIrKkB-AqXDBWJNtt7u_HLWvm87AAkOxCobuH5XieQEMaHi_v6JqLAUt1B3ZfnFLv5wRCIRumzSo2FksiIe
     */
}
