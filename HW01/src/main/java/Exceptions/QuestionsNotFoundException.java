package Exceptions;

import java.io.FileNotFoundException;

public class QuestionsNotFoundException extends RuntimeException {
    public QuestionsNotFoundException(FileNotFoundException e) {
        super(e);
    }
    public QuestionsNotFoundException() {

    }

}
