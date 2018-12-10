package exceptions;

public class InvalidTokenFormat extends RuntimeException {
    public InvalidTokenFormat(String s) {
        super(s);
    }
}
