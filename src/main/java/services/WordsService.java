package services;

import exceptions.DatabaseError;
import exceptions.InvalidWordException;
import helpers.WordValidator;
import models.Word;
import repositories.WordsRepository;

import java.util.LinkedList;
import java.util.List;

public class WordsService {
    private WordsRepository wordsRepository;

    public WordsService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public List<Word> getAllWords() {
        return wordsRepository.getAllWords();
    }

    public Word getWordById(long id) {
        return wordsRepository.getWordById(id);
    }

    public Word addNewWord(String wordToAdd) {
        if(!WordValidator.isWordValid(wordToAdd)){
            throw new InvalidWordException("Given word: "+wordToAdd+" is invalid");
        }
        Word word = new Word(wordToAdd);
        if(wordsRepository.addWord(word)){
            return word;
        }else {
            throw new DatabaseError();
        }
    }

    public List<Word> addWordsList(List<String> words) {
        List<Word> wordsAdded = new LinkedList<>();
        for (String word: words) {
            wordsAdded.add(addNewWord(word));
        }
        return wordsAdded;
    }
    public long getWordsSum(){
        return wordsRepository.getWordsSum();
    }
}
