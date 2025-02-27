package caviar.exception;

/**
 * Represents an exception specific to the Caviar application.
 */
public class CaviarException extends Exception {
    public CaviarException(String message) {
        super("roe..!! " + message);
    }
}
