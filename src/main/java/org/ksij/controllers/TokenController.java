package org.ksij.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.Token;
import org.ksij.models.utilisateurs.Utilisateur;

@Path("token")
public class TokenController {

    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Token token){
        token.persist();
        return Response.ok().build();
    }
}
