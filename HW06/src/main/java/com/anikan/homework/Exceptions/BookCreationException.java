package com.anikan.homework.Exceptions;

public class BookCreationException extends RuntimeException {
    public BookCreationException(Exception ex) {
        super(ex);
    }
}
