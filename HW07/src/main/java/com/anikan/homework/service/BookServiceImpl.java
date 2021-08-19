package com.anikan.homework.service;

import com.anikan.homework.dto.BookDto;
import com.anikan.homework.exceptions.BookCreationException;
import com.anikan.homework.exceptions.NoSuchAuthorException;
import com.anikan.homework.exceptions.NoSuchBookException;
import com.anikan.homework.exceptions.NoSuchGenreException;
import com.anikan.homework.dao.BookDao;
import com.anikan.homework.domain.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class BookServiceImpl implements BookService{
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    @Transactional(readOnly = true)
    @Override
    public BookDto getById(Long id) {
        BookDto bookDto = null;
        try {
            Book book = bookDao.getById(id);
            if (isNull(book)){
                throw new NoSuchBookException("There is no book with such ID");
            }
            bookDto = new BookDto(book);
        } catch (EmptyResultDataAccessException exception){
            throw new NoSuchBookException(exception);
        }

        return bookDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return bookDao.getAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long insertNew(Book book) {
        try {
            return bookDao.insert(book);
        } catch (Exception ex) {
            throw new BookCreationException(ex);
        }

    }

    @Override
    @Transactional
    public BookDto update(BookDto book) {
        BookDto resultBook = null;
        try {
            Book bookToUpdate = bookDao.getById(book.getId());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setGenre(book.getGenre());
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setComments(book.getComments());
            resultBook = new BookDto(bookDao.update(bookToUpdate));
        } catch (EntityNotFoundException exception){ //todo посмотреть логику с ошибками
            if (Objects.requireNonNull(exception.getMessage()).toLowerCase().contains("author"))
                throw new NoSuchAuthorException(exception);
            if (exception.getMessage().toLowerCase().contains("genre"))
                throw new NoSuchGenreException(exception);
        }
        return resultBook;

    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookDao.deleteById(id);
    }
}
