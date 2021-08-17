package com.anikan.homework.exceptions;

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
