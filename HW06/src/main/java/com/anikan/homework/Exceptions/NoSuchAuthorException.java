package com.anikan.homework.Exceptions;

public class NoSuchAuthorException extends RuntimeException {
    public NoSuchAuthorException(Exception exception) {
        super(exception);
    }

    public NoSuchAuthorException(String message) {
        super(message);
    }
}