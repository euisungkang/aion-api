package newsbite.exception;

public class RESTConnectionException extends RuntimeException {

    public RESTConnectionException(String message) {
        super(message);
    }

    public RESTConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
