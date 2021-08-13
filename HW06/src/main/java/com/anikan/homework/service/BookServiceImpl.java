package com.anikan.homework.service;

import com.anikan.homework.Exceptions.BookCreationException;
import com.anikan.homework.Exceptions.NoSuchAuthorException;
import com.anikan.homework.Exceptions.NoSuchBookException;
import com.anikan.homework.Exceptions.NoSuchGenreException;
import com.anikan.homework.dao.BookDao;
import com.anikan.homework.domain.Book;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;

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
        } catch (Exception ex) {
            throw new BookCreationException(ex);
        }

    }

    @Override
    public Book update(Book book) {
        Book resultBook = null;
        try {
            resultBook = bookDao.update(book);
        } catch (EntityNotFoundException exception){ //todo посмотреть логику с ошибками
            if (Objects.requireNonNull(exception.getMessage()).toLowerCase().contains("author"))
                throw new NoSuchAuthorException(exception);
            if (exception.getMessage().toLowerCase().contains("genre"))
                throw new NoSuchGenreException(exception);
        }
        return resultBook;

    }
}
