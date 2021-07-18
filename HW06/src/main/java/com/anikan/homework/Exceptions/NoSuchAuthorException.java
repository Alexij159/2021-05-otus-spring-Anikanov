package com.anikan.homework.Exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class NoSuchAuthorException extends RuntimeException {
    public NoSuchAuthorException(Exception exception) {
        super(exception);
    }
}