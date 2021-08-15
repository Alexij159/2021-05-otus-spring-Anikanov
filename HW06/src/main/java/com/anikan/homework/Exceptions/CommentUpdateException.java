package com.anikan.homework.Exceptions;

public class CommentUpdateException extends RuntimeException {
    public CommentUpdateException() {
    }

    public CommentUpdateException(String message) {
        super(message);
    }

    public CommentUpdateException(Throwable cause) {
        super(cause);
    }
}
