package com.anikan.homework.service;

import com.anikan.homework.domain.Book;

import java.util.List;

public interface BookService {
    Book getById(Long id);
    List<Book> getAll();
    Long insertNew(Book book);
    Book update(Book book);
    void delete(Long id);
}
