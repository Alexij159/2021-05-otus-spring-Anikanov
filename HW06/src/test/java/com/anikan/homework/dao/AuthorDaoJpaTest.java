package com.anikan.homework.dao;

import com.anikan.homework.Exceptions.AuthorUpdateException;
import com.anikan.homework.Exceptions.NoSuchAuthorException;
import com.anikan.homework.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long NONEXISTING_AUTHOR_ID = 1000L;
    private static final String NEW_COOL_AUTHOR = "New Cool Author";


    @Autowired
    private AuthorDaoJpa authorDao;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void getByIdNormalWork() {
        Author b = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(authorDao.getById(EXISTING_AUTHOR_ID)).usingRecursiveComparison().isEqualTo(b);
    }

    @Test
    void getByIdShouldReturnNull(){
        assertThat(authorDao.getById(3L)).isNull();
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
        assertThat(authorDao.getById(id)).usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(testEntityManager.find(Author.class,id));
    }


    @Test
    void updateNormalWork() {
//        Author a = new Author(EXISTING_AUTHOR_ID,"Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        Author a = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        String oldName = a.getFullName();
        testEntityManager.detach(a);

        a.setFullName(NEW_COOL_AUTHOR);
        authorDao.update(a);
        Author updatedAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(updatedAuthor.getFullName()).isNotEqualTo(oldName).isEqualTo(NEW_COOL_AUTHOR);
    }

    @Test
    void updateShouldThrowException() {
        Author a = new Author("Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        assertThrows(AuthorUpdateException.class,() -> authorDao.update(a));
    }



    @Test
    void deleteByShouldNotDeleteDependentAuthor() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> authorDao.deleteById(EXISTING_AUTHOR_ID))
                .isInstanceOf(PersistenceException.class);
    }

}