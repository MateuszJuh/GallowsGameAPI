package controllers;

import models.WordDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/")
public class WordsController {

    @GET
    public List<WordDto> getAllWords(){ //TODO getAllWords()
        return null;
    }
}
