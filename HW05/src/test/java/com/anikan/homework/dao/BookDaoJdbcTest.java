package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final long EXISTING_BOOK_ID = 1L;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;
    private final Genre g1 = new Genre(1L,"поэма");
    private final Genre g2 = new Genre(2L,"стих");
    private final Author a1 = new Author(1L, "Пушкин Александр Сергеевич", "Пушкин А.С.", LocalDate.of(1799, 6, 06));
    private final Author a2 = new Author(2L, "Есенин Сергей Александрович", "Есенин С.А.", LocalDate.of(1895, 9, 21));

    @Test
    void getByIdNormalWork() {
        Book b = new Book(1L, "Поэма1", a2, g1);
        assertThat(bookDaoJdbc.getById(EXISTING_BOOK_ID)).usingRecursiveComparison().isEqualTo(b);
    }

    @Test
    void getByIdShouldThrowEmptyResultDataAccessException() {
        assertThatThrownBy(() -> bookDaoJdbc.getById(1000L)).isInstanceOf(EmptyResultDataAccessException.class);
    }


    @Test
    void getAllNormalWork() {

        List<Book> books = List.of(new Book(1L, "Поэма1", a2, g1),
                new Book(2L, "Поэма2", a1, g1),
                new Book(3L, "Стих1", a1, g2),
                new Book(4L, "Стих2", a2, g2));
        assertThat(bookDaoJdbc.getAll()).usingRecursiveComparison().isEqualTo(books);
    }

    @Test
    void insertNormalWork() {
        Book b = new Book( "Поэма1", a2, g1);
        long id = bookDaoJdbc.insert(b);
        assertThat(bookDaoJdbc.getById(id)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(b);
    }


    @Test
    void updateShouldThrowExceptionByAuthor(){
        Book book =new Book( EXISTING_BOOK_ID,"Поэмаololo", new Author(4L,"","",null), g1);
        assertThatThrownBy(() -> bookDaoJdbc.update(book)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void updateNormalWork() {
        Book book =new Book( 1L,"Поэмаololo", a1, g1);
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