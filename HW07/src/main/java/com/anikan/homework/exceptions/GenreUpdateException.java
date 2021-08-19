package com.anikan.homework.exceptions;

public class GenreUpdateException extends RuntimeException {
    public GenreUpdateException() {
    }

    public GenreUpdateException(String message) {
        super(message);
    }

    public GenreUpdateException(Throwable cause) {
        super(cause);
    }
}
