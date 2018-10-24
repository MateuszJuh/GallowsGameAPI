package services;

import models.Word;
import repositories.WordsRepository;

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
        return null;
    }

    public List<Word> addWordsList(List<String> words) {
        return null;
    }
    public long getWordsSum(){
        return wordsRepository.getWordsSum();
    }
}
