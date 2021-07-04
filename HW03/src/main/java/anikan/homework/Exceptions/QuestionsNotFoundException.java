package anikan.homework.Exceptions;

public class QuestionsNotFoundException extends RuntimeException {
    public QuestionsNotFoundException(Throwable cause) {
        super(cause);
    }
    public QuestionsNotFoundException(String message) {
        super(message);
    }
}
