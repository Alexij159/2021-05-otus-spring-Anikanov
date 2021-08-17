package com.anikan.homework.exceptions;

public class NoSuchCommentException extends RuntimeException {
    public NoSuchCommentException() {
    }

    public NoSuchCommentException(String message) {
        super(message);
    }

    public NoSuchCommentException(Throwable cause) {
        super(cause);
    }
}
