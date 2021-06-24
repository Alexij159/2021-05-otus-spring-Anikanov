package com.anikan.homework.service;

import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;

import java.util.List;

public interface BookService {
    Book getById(Long id);
    List<Book> getAll();
    Long insertNew(Book book);
    boolean update(Long id, Book book);
    boolean update(Book book);
}
