package repositories;

import com.sun.jersey.spi.inject.Inject;
import exceptions.WordAlreadyExistsException;
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
    public boolean addWord(Word word){
        if(!contains(word.getWord())){
            entityManager.persist(word);
            return true;
        }else {
            throw new WordAlreadyExistsException("Word: " + word.getWord() + " already exists");
        }
    }

    private boolean contains(String word){
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM words WHERE word=" + word);
        if(nativeQuery.getMaxResults()>0){
            return true;
        }else {
            return false;
        }
    }
}
