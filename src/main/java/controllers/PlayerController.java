package controllers;


import repositories.PlayerRepository;
import services.PlayerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("player/")
public class PlayerController { //TODO PlayerController

    private PlayerService playerService = new PlayerService(new PlayerRepository());

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean login(String encodedToken){
        return false;
    }


    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean register(String encodedToken){
        return playerService.register(encodedToken);
    }

    @POST
    @Path("increase")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int increaseScore(String encodedToken){
        return playerService.increaseScore(encodedToken);
    }
}
