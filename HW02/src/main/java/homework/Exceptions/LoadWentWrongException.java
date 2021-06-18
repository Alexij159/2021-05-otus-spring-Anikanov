package homework.Exceptions;

public class LoadWentWrongException extends RuntimeException {
    public LoadWentWrongException(Throwable cause) {
        super(cause);
    }
    public LoadWentWrongException(String message) {
        super(message);
    }
}
