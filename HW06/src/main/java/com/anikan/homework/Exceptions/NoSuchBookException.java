package com.anikan.homework.Exceptions;

public class NoSuchBookException extends RuntimeException {
    public NoSuchBookException(Exception exception) {
        super(exception);
    }

    public NoSuchBookException(String message) {
        super(message);
    }
}
