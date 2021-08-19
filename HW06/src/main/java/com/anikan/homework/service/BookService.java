package com.anikan.homework.service;

import com.anikan.homework.domain.Book;
import com.anikan.homework.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto getById(Long id);
    List<BookDto> getAll();
    Long insertNew(Book book);
    BookDto update(BookDto book);
    void delete(Long id);
}
