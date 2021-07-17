package com.anikan.homework.dao;

import com.anikan.homework.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTest {
    private static final long EXISTING_GENRE_ID = 1L;
    private static final long NONEXISTING_GENRE_ID = 1000L;

    @Autowired
    private GenreDao genreDao;

    @Test
    void getByIdNormalWork() {
        Genre g = new Genre(EXISTING_GENRE_ID, "поэма");
        assertThat(genreDao.getById(g.getId())).usingRecursiveComparison().isEqualTo(g);
    }

    @Test
    void getByIdShouldThrowEmptyResultDataAccessException(){
        assertThatThrownBy(() -> genreDao.getById(3L)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getAllNormalWork() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1L, "поэма"));
        genres.add(new Genre(2L, "стих"));
        assertThat(genreDao.getAll()).usingRecursiveComparison().isEqualTo(genres);
    }

    @Test
    void insertNormalWork() {
        Genre g = new Genre("поэма");
        long id = genreDao.insert(g);
        assertThat(genreDao.getById(id)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(g);
    }

    @Test
    void updateByIdNormalWork() {
        Genre g = new Genre("роман");
        assertThat(genreDao.updateById(EXISTING_GENRE_ID, g)).isTrue();
        assertThat(genreDao.getById(EXISTING_GENRE_ID)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(g);
    }

    @Test
    void updateNormalWork() {
        Genre g = new Genre(EXISTING_GENRE_ID, "роман");
        assertThat(genreDao.update(g)).isTrue();
        assertThat(genreDao.getById(EXISTING_GENRE_ID)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(g);
    }

    @Test
    void updateShouldThrowException() {
        Genre g = new Genre("роман");
        assertThrows(RuntimeException.class,() -> genreDao.update(g));
    }


    @Test
    void updateByIdShouldReturnFalse() {
        Genre g = new Genre("роман");
        assertThat(genreDao.updateById(NONEXISTING_GENRE_ID, g)).isFalse();
    }


    @Test
    void deleteByShouldNotDeleteDependentAuthor() {
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> genreDao.deleteById(EXISTING_GENRE_ID))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}