package com.anikan.homework.dao;

import com.anikan.homework.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final long EXISTING_BOOK_ID = 1L;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void getByIdNormalWork() {
        Book b = new Book(1L, "Поэма1", 2L, 1L);
        assertThat(bookDaoJdbc.getById(EXISTING_BOOK_ID)).usingRecursiveComparison().isEqualTo(b);
    }

    @Test
    void getByIdShouldThrowEmptyResultDataAccessException() {
        assertThatThrownBy(() -> bookDaoJdbc.getById(1000L)).isInstanceOf(EmptyResultDataAccessException.class);
    }


    @Test
    void getAllNormalWork() {
        List<Book> books = List.of(new Book(1L, "Поэма1", 2L, 1L),
                new Book(2L, "Поэма2", 1L, 1L),
                new Book(3L, "Стих1", 1L, 2L),
                new Book(4L, "Стих2", 2L, 2L));
        assertThat(bookDaoJdbc.getAll()).usingRecursiveComparison().isEqualTo(books);
    }

    @Test
    void insertNormalWork() {
        Book b = new Book( "Поэма1", 2L, 1L);
        long id = bookDaoJdbc.insert(b);
        assertThat(bookDaoJdbc.getById(id)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(b);
    }

    @Test
    void updateByIdNormalWork() {
        Book book =new Book( "Поэмаololo", 1L, 1L);
        assertThat(bookDaoJdbc.updateById(EXISTING_BOOK_ID, book)).isTrue();
        assertThat(bookDaoJdbc.getById(EXISTING_BOOK_ID)).usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(book);
    }

    @Test
    void updateByIdShouldThrowExceptionByAuthor(){
        Book book =new Book( "Поэмаololo", 4L, 1L);
        assertThatThrownBy(() -> bookDaoJdbc.updateById(EXISTING_BOOK_ID, book)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void updateNormalWork() {
        Book book =new Book( 1L,"Поэмаololo", 1L, 1L);
        assertThat(bookDaoJdbc.update(book)).isTrue();
        assertThat(bookDaoJdbc.getById(EXISTING_BOOK_ID)).usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(book);
    }

    @Test
    void deleteByIdNormalWork() {
        assertThatCode(() -> bookDaoJdbc.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDaoJdbc.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDaoJdbc.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}