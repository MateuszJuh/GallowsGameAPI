package exceptions;

public class PlayerAuthenticationException extends RuntimeException {
    public PlayerAuthenticationException(String s) {
        super(s);
    }
}
