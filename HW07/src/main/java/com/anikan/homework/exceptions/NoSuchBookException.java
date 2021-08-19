package com.anikan.homework.exceptions;

public class NoSuchBookException extends RuntimeException {
    public NoSuchBookException(Exception exception) {
        super(exception);
    }

    public NoSuchBookException(String message) {
        super(message);
    }
}
