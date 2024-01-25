package org.ksij.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ksij.models.priere.Priere;
import org.ksij.models.utilisateurs.Utilisateur;

import java.util.HashMap;
import java.util.List;

@Path("utilisateur")
public class UtilisateurController {


    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Utilisateur> getAllEvenement() {
        return Utilisateur.listAll();
    }
    @GET
    @Path("login/{mdp}/{telephone}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUtilisateur(@PathParam("mdp") String mdp,
                                        @PathParam("telephone") String telephone) {
        HashMap params = new HashMap();
        params.put("mdp",mdp);
        params.put("telephone",telephone);
        Utilisateur utilisateur = (Utilisateur) Utilisateur.find("mdp =: mdp and telephone =: telephone",params).firstResult();
        //
        if(utilisateur != null){
            return Response.ok(utilisateur).build();
        }else{
            HashMap rep = new HashMap();
            rep.put("compte","vide");
            return Response.ok(rep).build();
        }
    }


    @POST
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveEvenement(Utilisateur utilisateur){
        utilisateur.persist();
        return Response.ok().build();
    }

    @PUT
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putEvenement(Utilisateur utilisateur){
        Utilisateur utilisateur1 = Utilisateur.findById(utilisateur.id);
        if(utilisateur1 == null){
            return Response.serverError().build();
        }
        utilisateur1.niveau = utilisateur.niveau;
        utilisateur1.nom = utilisateur.nom;
        utilisateur1.mdp = utilisateur.mdp;
        utilisateur1.maman = utilisateur.maman;
        utilisateur1.telephone = utilisateur.telephone;


        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEvenement(@PathParam("id") Long id){
        boolean r =  Utilisateur.deleteById(id);
        if(!r){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}
