package com.anikan.homework.exceptions;

public class BookCreationException extends RuntimeException {
    public BookCreationException(Exception ex) {
        super(ex);
    }
}
