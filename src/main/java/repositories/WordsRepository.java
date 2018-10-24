package repositories;

import com.sun.jersey.spi.inject.Inject;
import models.Word;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class WordsRepository {

    @Inject
    EntityManager entityManager;

    public List<Word>getAllWords(){
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM words");
        return (List<Word>) nativeQuery.getResultList();
    }
    public Word getWordById(long id){
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM words WHERE id=" + id);
        return (Word)nativeQuery.getSingleResult();
    }
    public long getWordsSum(){
        Query nativeQuery = entityManager.createNativeQuery("SELECT COUNT (id) FROM words");
        return (Long)nativeQuery.getSingleResult();
    }
    public boolean addWord(String word){
        return false;
    }
}
