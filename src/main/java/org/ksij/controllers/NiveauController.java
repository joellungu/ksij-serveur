package org.ksij.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.live.Live;
import org.ksij.models.niveau.Cours;
import org.ksij.models.niveau.Niveau;
import java.io.IOException;
import java.util.List;

@Path("niveau")
public class NiveauController {
    //
    @GET
    @Path("file/{niveau}")
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    public Response getLastLive(@PathParam("niveau") int niveau) {
        //
        Niveau niveau1 = Niveau.find("niveau",niveau).firstResult();

        return Response.ok(niveau1.titre).build();
    }

    @GET
    @Path("f/cours/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getFile(@PathParam("id") long id) throws IOException {
        Cours cours = Cours.findById(id);
        return cours.cours;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLive() {
        //
        return Response.ok(Niveau.listAll()).build();
    }

    @GET
    @Path("all/cours/{niveau}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCours(@PathParam("niveau") int niveau) {
        //
        List<Cours> cours = Cours.find("niveau", niveau).list();
        //
        cours.forEach((c)->{
            //
            c.cours = new byte[0];
        });
        //
        return Response.ok(cours).build();
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveNiveau(Niveau niveau){
        niveau.persist();
        return Response.ok(niveau.id).build();
    }

    @POST
    @Path("cours")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveCours(Cours cours){
        cours.persist();
        return Response.ok(cours.id).build();
    }

    @POST
    @Path("cours/{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    @Transactional
    public Response saveFile( @PathParam("id") Long id, byte[] fichier){
        Cours nv = Cours.findById(id);
        nv.cours = fichier;
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    @Transactional
    public Response supprimerUnNiveau(@PathParam("id") Long id){
        Niveau.deleteById(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("cours/{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    @Transactional
    public Response supprimerUnCour(@PathParam("id") Long id){
        Cours.deleteById(id);
        return Response.ok().build();
    }
}
