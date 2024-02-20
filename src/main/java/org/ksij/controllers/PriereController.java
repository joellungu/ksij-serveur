package org.ksij.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.EvenNotification;
import org.ksij.models.calendrier.Calendrier;
import org.ksij.models.infos.Infos;
import org.ksij.models.priere.Priere;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Path("priere")
public class PriereController {

    @Inject
    EvenNotification evenNotification;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Priere> getAllEvenement() {
        //
        return Priere.listAll();
    }

    @GET
    @Path("one/{date}/{heure}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Priere> getOneEvenement(@PathParam("date") String date,
                                        @PathParam("heure") String heure) {
        /*
        try {
            priereNotification(date,heure);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        */
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
            String d = priere.dateTime;
            priere.horaires.forEach((p)->{
                //
                try {
                    priereNotification(d,"17:51",p.priere);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
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
        //
        String d = priere.dateTime;
        priere.horaires.forEach((p)->{
            //
            try {
                priereNotification(d,p.heure,p.priere);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        //
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
                String d = priere.dateTime;
                priere1.horaires.forEach((p)->{
                    //
                    try {
                        priereNotification(d,p.heure,p.priere);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
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

    private void priereNotification(String jour, final String heure, String priere) throws ParseException {
        //
        System.out.println("Task jour: " + jour + "heure: " +heure+
                "priere: " + priere);
        //
        TimerTask task = new TimerTask() {
            public void run() {
                //
                HashMap<String, String> e = new HashMap<>();
                //e.put("id", ""+evenement.id);
                e.put("titre", "Namaaz Timings");
                e.put("contenu", priere+" "+heure+"");
                e.put("topic", priere);
                //e.put("id", ""+evenement.);

                try {
                    evenNotification.rappel(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //
                System.out.println("Task performed on: " + new Date() + "n" +
                        "Thread's name: " + Thread.currentThread().getName());
            }
        };
        Timer timer = new Timer("Timer");
        //
        String[] sDate = jour.split("-");
        String j = sDate[0].length() == 1 ? "0"+sDate[0] : sDate[0];
        String m = sDate[1].length() == 1 ? "0"+sDate[1] : sDate[1];
        String a = sDate[2];
        //
        String sHeure = heure.replace(" ", "");
        sHeure = sHeure.replace("min", "");
        sHeure = sHeure.replace("h", "");
        String[] ssHeure = sHeure.split(":");
        String hh = ssHeure[0].length() == 1 ? "0"+ssHeure[0] : ssHeure[0];
        String mm = ssHeure[1].length() == 1 ? "0"+ssHeure[1] : ssHeure[1];
        String ss = "00";
        //
        String myDate = a+"/"+m+"/"+j+" "+hh+":"+mm+":"+ss;

        System.out.println("Le temps milli: "+myDate);
        //
        LocalDateTime localDateTime = LocalDateTime.now();
        //
        //localDateTime.
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.of(Integer.parseInt(a),Integer.parseInt(m),
                Integer.parseInt(j),Integer.parseInt(hh),Integer.parseInt(mm));
        //
        LocalDateTime mt = LocalDateTime.now();
        //
        long com = mt.compareTo(date);
        long moc = date.compareTo(mt);
        //long millis = date.getTime();
        //
        System.out.println("Le temps milli: "+com+"::"+moc);

        long delay = 1000L;
        System.out.println("Le temps milli: "+com+"::"+moc);

        //
        //LocalDateTime datee = LocalDateTime.of(2024, 1, 26, 13, 25);

        //System.out.println("Initial Epoch (TimeInMillis): " + datee.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
        System.out.println("Initial Epoch Now: " + mt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
        System.out.println("Initial Epoch Send: " + date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
        Long f1 =mt.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        Long f2 = date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
        System.out.println("Initial Epoch resultat: " + (f1 - f2));
        System.out.println("Initial Epoch resultat: " + (f2 - f1));
        //
        if((f2 - f1) < 0){
            timer.schedule(task, (f1 - f2));
        }else{
            timer.schedule(task, (f2 - f1));
        }
        //


    }

}
