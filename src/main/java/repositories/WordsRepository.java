package repositories;

import exceptions.WordAlreadyExistsException;
import exceptions.WordNotFoundException;
import models.Word;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class WordsRepository {

    EntityManager entityManager = Persistence.createEntityManagerFactory("WordsPersistenceUnit").createEntityManager();

    public List<Word>getAllWords(){
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM words");
        return (List<Word>) nativeQuery.getResultList();
    }
    public Word getWordById(long id){
        if(containsId(id)) {
            return entityManager.find(Word.class, id);
        }else {
            throw new WordNotFoundException("Word with id: " + id + " not found");
        }
    }

    public long getWordsSum(){
        Query nativeQuery = entityManager.createNativeQuery("SELECT COUNT (id) FROM words");
        return (Long)nativeQuery.getSingleResult();
    }
    public boolean addWord(Word word){
        if(!containsWord(word.getWord())){
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(word);
            entityManager.flush();
            transaction.commit();
            return true;
        }else {
            throw new WordAlreadyExistsException("Word: " + word.getWord() + " already exists");
        }
    }

    private boolean containsId(long id) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT COUNT(id) FROM words WHERE id=" + id);
        BigInteger resultsSum = (BigInteger)nativeQuery.getSingleResult();
        if(resultsSum.longValue()>0){
            return true;
        }else {
            return false;
        }
    }

    private boolean containsWord(String word){
        Query nativeQuery = entityManager.createNativeQuery("SELECT COUNT(word) FROM words WHERE word='" + word + "'");
        BigInteger resultsSum = (BigInteger)nativeQuery.getSingleResult();
        if(resultsSum.longValue() > 0){
            return true;
        }else {
            return false;
        }
    }
}
