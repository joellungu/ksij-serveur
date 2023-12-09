package org.ksij.controllers;

import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.live.Live;
import org.ksij.models.niveau.Niveau;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

        return Response.ok(niveau1.cours).build();
    }

    @GET
    @Path("f/{niveau}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public File getFile(@PathParam("niveau") String niveau) throws IOException {
        Niveau niveau1 = Niveau.find("niveau",niveau).firstResult();
        //
        String str = "Hello";
        FileOutputStream outputStream = new FileOutputStream("pdf"+niveau);
        byte[] strToBytes = str.getBytes();
        outputStream.write(niveau1.cours);

        outputStream.close();
        //
        File nf = new File("pdf"+niveau);

        //log.info("file:" + nf.exists());
        //Response.ResponseBuilder response = Response.ok((Object) nf);
        //response.header("Content-Disposition", "attachment;filename=" + nf);
        //Uni<Response> re = Uni.createFrom().item(response.build());
        return nf;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLive() {
        //
        return Response.ok(Niveau.listAll()).build();
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Niveau niveau){
        niveau.persist();
        return Response.ok(niveau.id).build();
    }

    @POST
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    @Transactional
    public Response saveFile( @PathParam("id") Long id, byte[] fichier){
        Niveau nv = Niveau.findById(id);
        nv.cours = fichier;
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    @Transactional
    public Response supprimerUnCour( @PathParam("id") Long id, byte[] fichier){
        Niveau.deleteById(id);
        return Response.ok().build();
    }
}
