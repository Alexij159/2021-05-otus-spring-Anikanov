package com.anikan.homework.Exceptions;

public class AuthorUpdateException extends RuntimeException {
    public AuthorUpdateException() {
    }

    public AuthorUpdateException(String message) {
        super(message);
    }

    public AuthorUpdateException(Throwable cause) {
        super(cause);
    }
}
