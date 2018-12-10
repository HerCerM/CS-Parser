public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message) {
        super("Invalid token at line: " + message);
    }
}
