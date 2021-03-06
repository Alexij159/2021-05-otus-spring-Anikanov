package com.anikan.homework.service;

import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre getById(Long id);
    List<Genre> getAll();
    Long insertNew(Genre genre);
    boolean update(Long id, Genre genre);
    boolean update(Genre genre);
}
