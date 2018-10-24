package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordValidator {
    public static boolean isWordValid(String word){
        if(word==null || word.trim().isEmpty()){
            return false;
        }
        Pattern alphabeticPattern = Pattern.compile("[a-zA-Z]*");
        Matcher alphabeticMatcher = alphabeticPattern.matcher(word);
        if(!alphabeticMatcher.matches()){
            return false;
        }
        Pattern whiteSpacePattern = Pattern.compile("\\s");
        Matcher whiteSpaceMatcher = whiteSpacePattern.matcher(word);
        if(whiteSpaceMatcher.matches()){
            return false;
        }
        return true;
    }
}
