package com.anikan.homework.Exceptions;

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
