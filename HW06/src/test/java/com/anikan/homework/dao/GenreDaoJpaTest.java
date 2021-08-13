package com.anikan.homework.dao;

import com.anikan.homework.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(GenreDaoJpa.class)
public class GenreDaoJpaTest {
    private static final long EXISTING_GENRE_ID = 1L;
    private static final long NONEXISTING_GENRE_ID = 1000L;
    private static final String NEW_GENRE_NAME = "поэма-фигема";

    @Autowired
    private GenreDaoJpa genreDao;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void getByIdNormalWork() {
        Genre g = new Genre(EXISTING_GENRE_ID, "поэма");
        assertThat(genreDao.getById(EXISTING_GENRE_ID)).usingRecursiveComparison().isEqualTo(g);
    }

    @Test
    void getByIdShouldReturnNull(){
        assertThat(genreDao.getById(3L)).isNull();
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
        assertThat(genreDao.getById(id)).usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(testEntityManager.find(Genre.class, id));
    }

    @Test
    void updateNormalWork() {
        Genre g = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        String oldName = g.getName();
        testEntityManager.detach(g);

        g.setName(NEW_GENRE_NAME);
        genreDao.update(g);
        Genre updatedGenre = testEntityManager.find(Genre.class,EXISTING_GENRE_ID);
        assertThat(updatedGenre.getName()).isNotEqualTo(oldName).isEqualTo(NEW_GENRE_NAME);
    }

    @Test
    void updateShouldThrowException() {
        Genre g = new Genre("роман");
        assertThrows(RuntimeException.class,() -> genreDao.update(g));
    }



    @Test
    void deleteByShouldNotDeleteDependentAuthor() {
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> genreDao.deleteById(EXISTING_GENRE_ID))
                .isInstanceOf(PersistenceException.class);
    }
}