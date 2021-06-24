package com.anikan.homework.service;

import com.anikan.homework.Exceptions.BookCreationException;
import com.anikan.homework.Exceptions.NoSuchBookException;
import com.anikan.homework.dao.BookDao;
import com.anikan.homework.domain.Book;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getById(Long id) {
        Book book = null;
        try {
            book = bookDao.getById(id);
        } catch (EmptyResultDataAccessException exception){
            throw new NoSuchBookException(exception);
        }

        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Long insertNew(Book book) {
        try {
            return bookDao.insert(book);
        } catch (DataIntegrityViolationException ex) {
            throw new BookCreationException(ex);
        }

    }

    @Override
    public boolean update(Long id, Book book) {
        return bookDao.updateById(id, book);
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book);
    }
}
