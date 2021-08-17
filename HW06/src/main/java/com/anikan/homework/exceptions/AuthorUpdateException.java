package com.anikan.homework.exceptions;

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
