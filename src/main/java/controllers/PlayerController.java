package controllers;


import models.ResponseWithPlayer;
import repositories.PlayerRepository;
import services.PlayerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("player/")
public class PlayerController {

    private PlayerService playerService = new PlayerService(new PlayerRepository());

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseWithPlayer login(Token encodedToken){
        return playerService.login(encodedToken.getToken());
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseWithPlayer register(Token encodedToken){
        return playerService.register(encodedToken.getToken());
    }

    @POST
    @Path("increase")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseWithPlayer increaseScore(Token encodedToken){
        return playerService.increaseScore(encodedToken.getToken());
    }

    public static class Token{
       private String token;

        public Token() {
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
