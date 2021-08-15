package com.anikan.homework.Exceptions;

public class BookUpdateException extends RuntimeException {
    public BookUpdateException() {
    }

    public BookUpdateException(String message) {
        super(message);
    }

    public BookUpdateException(Throwable cause) {
        super(cause);
    }
}
