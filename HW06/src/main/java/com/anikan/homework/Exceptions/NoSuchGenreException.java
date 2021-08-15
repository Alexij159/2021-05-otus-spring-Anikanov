package com.anikan.homework.Exceptions;

public class NoSuchGenreException extends RuntimeException {
    public NoSuchGenreException(Exception exception) {
        super(exception);
    }

    public NoSuchGenreException(String message) {
        super(message);
    }
}
