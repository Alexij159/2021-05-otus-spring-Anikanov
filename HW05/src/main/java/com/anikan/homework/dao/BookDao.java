package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;

import java.util.List;

public interface BookDao {
    Book getById(Long id);
    List<Book> getAll();
    Long insert(Book book);
    boolean update(Book book);
    void deleteById(Long id);
}
