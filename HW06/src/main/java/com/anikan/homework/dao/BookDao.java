package com.anikan.homework.dao;

import com.anikan.homework.domain.Book;

import java.util.List;

public interface BookDao {
    Book getById(Long id);
    List<Book> getAll();
    Long insert(Book book);
    Book update(Book book);
    void deleteById(Long id);
}
