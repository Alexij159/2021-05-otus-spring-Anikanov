package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long NONEXISTING_AUTHOR_ID = 1000L;

    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    void getByIdNormalWork() {
        Author a = new Author(EXISTING_AUTHOR_ID,"Пушкин Александр Сергеевич", "Пушкин А.С.",  LocalDate.of(1799, 6, 6));
        assertThat(authorDao.getById(a.getId())).usingRecursiveComparison().isEqualTo(a);
    }

    @Test
    void getByIdShouldThrowEmptyResultDataAccessException(){
        assertThatThrownBy(() -> authorDao.getById(3L)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getAllNormalWork() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L,"Пушкин Александр Сергеевич", "Пушкин А.С.",  LocalDate.of(1799, 6, 6)));
        authors.add(new Author(2L,"Есенин Сергей Александрович", "Есенин С.А.",  LocalDate.of(1895, 9, 21)));
        assertThat(authorDao.getAll()).usingRecursiveComparison().isEqualTo(authors);
    }

    @Test
    void insertNormalWork() {
        Author a = new Author( "Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        long id = authorDao.insert(a);
        assertThat(authorDao.getById(id)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(a);
    }


    @Test
    void updateNormalWork() {
        Author a = new Author(EXISTING_AUTHOR_ID,"Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        assertThat(authorDao.update(a)).isTrue();
        assertThat(authorDao.getById(EXISTING_AUTHOR_ID)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(a);
    }

    @Test
    void updateShouldThrowException() {
        Author a = new Author("Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        assertThrows(RuntimeException.class,() -> authorDao.update(a));
    }


    @Test
    void updateReturnFalse() {
        Author a = new Author(NONEXISTING_AUTHOR_ID,"Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        assertThat(authorDao.update(a)).isFalse();
    }


    @Test
    void deleteByShouldNotDeleteDependentAuthor() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> authorDao.deleteById(EXISTING_AUTHOR_ID))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}