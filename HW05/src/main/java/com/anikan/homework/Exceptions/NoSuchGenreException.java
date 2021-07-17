package com.anikan.homework.Exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class NoSuchGenreException extends RuntimeException {
    public NoSuchGenreException(Exception exception) {
        super(exception);
    }
}
