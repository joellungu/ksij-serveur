package org.ksij.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.infos.Infos;
import org.ksij.models.live.Live;

import java.util.List;

@Path("live")
public class LiveController {
    @GET
    @Path("live")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastLive() {
        //
        List<Live> lives = Live.listAll();
        if(lives.size() == 0){
            return Response.status(400).build();
        }
        return Response.ok(lives.get(lives.size() - 1)).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLive() {
        //
        return Response.ok(Live.listAll()).build();
    }

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Live live){
        live.persist();
        return Response.ok().build();
    }
}
