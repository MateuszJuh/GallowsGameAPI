package controllers;

import models.Word;
import repositories.WordsRepository;
import services.WordsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class WordsController {
    WordsRepository wordsRepository = new WordsRepository();
    WordsService wordsService = new WordsService(wordsRepository);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Word> getAllWords(){ //TODO checkAllWords()
        return wordsService.getAllWords();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Word getWordById(@PathParam("id") long id){//TODO getWordById()
        return wordsService.getWordById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Word addNewWord(String wordToAdd){//TODO addNewWord
        return wordsService.addNewWord(wordToAdd);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Word> addWordsList(List<String> words){//TODO addWordsList
        return wordsService.addWordsList(words);
    }
}
