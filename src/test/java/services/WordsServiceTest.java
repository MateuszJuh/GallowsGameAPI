package services;

import exceptions.InvalidWordException;
import exceptions.WordAlreadyExistsException;
import exceptions.WordNotFoundException;
import models.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repositories.WordsRepository;

import java.util.LinkedList;
import java.util.List;

public class WordsServiceTest {
    private WordsRepository wordsRepository;
    private WordsService wordsService;
    private List<Word> wordList;
    private String notExistingWord = "notExistingWord";
    private String existingWord = "first";
    private String invalidWord = "invalid word";

    @Before
    public void prepare(){
        wordsRepository = Mockito.mock(WordsRepository.class);
        wordsService = new WordsService(wordsRepository);
        wordList = new LinkedList<Word>();
        wordList.add(new Word(1, "first"));
        wordList.add(new Word(2, "second"));
        prepareMocks();
    }

    private void prepareMocks(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(wordsRepository.getAllWords()).thenReturn(wordList);
        Mockito.when(wordsRepository.getWordById(1)).thenReturn(wordList.get(0));
        Mockito.when(wordsRepository.getWordById(2)).thenReturn(wordList.get(1));
        Mockito.when(wordsRepository.getWordById(3)).thenThrow(new WordNotFoundException("word not found"));
        Mockito.when(wordsRepository.addWord(notExistingWord)).thenReturn(true);
        Mockito.when(wordsRepository.addWord(existingWord)).thenThrow(new WordAlreadyExistsException("word alreadu added"));
        Mockito.when(wordsRepository.getWordsSum()).thenReturn(2L);
    }

    @Test
    public void getAllWords_gettingWordsFromRepository_returnedAllWords(){
        //when
        List<Word> allWords = wordsService.getAllWords();
        //then
        Assert.assertEquals(wordList, allWords);
    }

    @Test
    public void getWordById_gettingWordWithExistingId_wordWithIdReturned(){
        //when
        Word wordById = wordsService.getWordById(1);
        Word wordById2 = wordsService.getWordById(2);
        //then
        Assert.assertEquals(wordList.get(0), wordById);
        Assert.assertEquals(wordList.get(1), wordById2);
    }

    @Test(expected = WordNotFoundException.class)
    public void getWordById_gettingWordWithNotExistingId_throwWordNotFoundException(){
        //when
        wordsService.getWordById(3);
    }

    @Test
    public void addNewWord_addingNotExistingWord_returnAddedWord(){
        //when
        Word word = wordsService.addNewWord(notExistingWord);
        //then
        Assert.assertEquals(notExistingWord, word.getWord());
    }

    @Test(expected = WordAlreadyExistsException.class)
    public void addNewWord_addingAlreadyAddedWord_throwWordAlreadyExistsException(){
        //when
        wordsService.addNewWord(existingWord);
    }

    @Test(expected = InvalidWordException.class)
    public void addNewWord_addingInvalidWord_throwInvalidWordException(){
        //when
        wordsService.addNewWord(invalidWord);
    }

    @Test
    public void getWordsSum_countingWordsInDb_wordsNum(){
        //when
        long wordsSum = wordsService.getWordsSum();
        //then
        Assert.assertEquals(2L, wordsSum);
    }

    @Test
    public void addListOfWords_addingListOfWords_callWordRepositoryAddWordForEveryWord(){
        //given
        List<String> wordsToAdd = new LinkedList<String>();
        wordsToAdd.add("notExistingWord");
        wordsToAdd.add("anotherNotExistingWord");
        //when
        wordsService.addWordsList(wordsToAdd);
        //then
        Mockito.verify(wordsRepository, Mockito.times(2)).addWord(Matchers.anyString());
    }

    @Test(expected = WordAlreadyExistsException.class)
    public void addListOfWords_addingListOfWordsWithExistingWord_throwWordAlreadyExists(){
        //given
        List<String> wordsToAdd = new LinkedList<String>();
        wordsToAdd.add("notExistingWord");
        wordsToAdd.add(existingWord);
        //when
        wordsService.addWordsList(wordsToAdd);
    }







}