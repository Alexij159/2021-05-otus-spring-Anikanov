package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre getById(Long id);
    List<Genre> getAll();
    Long insert(Genre genre);
    boolean updateById(Long id, Genre genre);
    boolean update(Genre genre);
    void deleteById(Long id);
}
