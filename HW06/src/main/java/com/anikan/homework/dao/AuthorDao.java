package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);
    List<Author> getAll();
    Long insert(Author author);
    Author update(Author author);
    void deleteById(Long id);
}
