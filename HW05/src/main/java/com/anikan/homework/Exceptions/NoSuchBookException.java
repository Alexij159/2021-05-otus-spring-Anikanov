package com.anikan.homework.Exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class NoSuchBookException extends RuntimeException {
    public NoSuchBookException(Exception exception) {
        super(exception);
    }
}
