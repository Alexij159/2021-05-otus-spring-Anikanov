package com.anikan.homework.service;

import com.anikan.homework.domain.Author;

import java.util.List;

public interface AuthorService {
    Author getById(Long id);
    List<Author> getAll();
    Long insertNew(Author author);
    Author update(Author author);
    void deleteById(Long id);
}
