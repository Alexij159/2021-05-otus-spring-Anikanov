package com.anikan.homework.service;

import com.anikan.homework.Exceptions.BookCreationException;
import com.anikan.homework.Exceptions.NoSuchAuthorException;
import com.anikan.homework.Exceptions.NoSuchBookException;
import com.anikan.homework.Exceptions.NoSuchGenreException;
import com.anikan.homework.dao.BookDao;
import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Genre;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookServiceTest {
    @Mock
    BookDao bookDao;
    @Autowired
    BookServiceImpl bookService;

    private final Genre g1 = new Genre(1L,"поэма");
    private final Genre g2 = new Genre(2L,"стих");
    private final Author a1 = new Author(1L, "Пушкин Александр Сергеевич", "Пушкин А.С.", LocalDate.of(1799, 6, 06));
    private final Author a2 = new Author(2L, "Есенин Сергей Александрович", "Есенин С.А.", LocalDate.of(1895, 9, 21));
    private final Author NONEXIST_AUTHOR = new Author(200L, "Автор", "А", null);
    private final Genre NONEXIST_GENRE = new Genre(200L, "Жанр");
    private final List<Book> books = List.of(new Book(1L, "Поэма1", a2, g1),
            new Book(2L, "Поэма2", a1, g1),
            new Book(3L, "Стих1", a1, g2),
            new Book(4L, "Стих2", a2, g2));



    @Test
    public void getBookByIdNormalWork(){
        assertThat(bookService.getById(2L)).usingRecursiveComparison().isEqualTo(books.get(1));
    }

    @Test
    public void getBookShouldThrowException(){
        assertThrows(NoSuchBookException.class, () ->bookService.getById(100L));
    }

    @Test
    public void getBooksNormalWork(){
        assertThat(bookService.getAll()).usingRecursiveComparison().isEqualTo(books);
    }

    @Test
    public void insertBookNormalWork() {
        Book newBook = new Book("Title", a1, g1);
        assertThat(bookService.insertNew(newBook)).isGreaterThan(4L);
    }

    @Test
    public void insertBookShouldThrowException() {
        Book newBook = new Book(1L,"Title", a1, g1);
        assertThrows(BookCreationException.class, () -> bookService.insertNew(newBook));
    }

    @Test
    public void updateBookNormalWork() {
        Book book = new Book(1L,"Title", a1, g1);
        assertThat(bookService.update(book)).isTrue();
        assertThat(bookService.getById(book.getId())).usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    public void updateBookShouldReturnFalse() {
        Book book = new Book(100L,"Title", a1, g1);
        assertThat(bookService.update(book)).isFalse();
    }

    @Test
    public void updateBookShouldThrowAuthorException() {
        Book book = new Book(1L,"Title", NONEXIST_AUTHOR , g1);
        assertThrows(NoSuchAuthorException.class, () -> bookService.update(book));
    }


    @Test
    public void updateBookShouldThrowGenreException() {
        Book book = new Book(1L,"Title", a1 , NONEXIST_GENRE);
        assertThrows(NoSuchGenreException.class, () -> bookService.update(book));
    }




}
