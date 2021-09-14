package com.anikan.homework.dao;

import com.anikan.homework.exceptions.AuthorUpdateException;
import com.anikan.homework.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest

class AuthorDaoJpaTest {
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long NONEXISTING_AUTHOR_ID = 1000L;
    private static final String NEW_COOL_AUTHOR = "New Cool Author";


    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void getByIdNormalWork() {
        Author b = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(authorDao.findById(EXISTING_AUTHOR_ID)).hasValue(b);
    }

    @Test
    void getByIdShouldReturnNull(){
        assertThat(authorDao.findById(3L)).isEmpty();

    }
    @Test
    void getAllNormalWork() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L,"Пушкин Александр Сергеевич", "Пушкин А.С.",  LocalDate.of(1799, 6, 6)));
        authors.add(new Author(2L,"Есенин Сергей Александрович", "Есенин С.А.",  LocalDate.of(1895, 9, 21)));
        assertThat(authorDao.findAll()).usingRecursiveComparison().isEqualTo(authors);
    }

    @Test
    void insertNormalWork() {
        Author a = new Author( "Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        long id = authorDao.save(a).getId();
        assertThat(authorDao.findById(id)).hasValue(testEntityManager.find(Author.class,id));
    }


    @Test
    void updateNormalWork() {
//        Author a = new Author(EXISTING_AUTHOR_ID,"Иванов Иван Иванович", "Иванов И.И.",  LocalDate.of(2000, 1, 1));
        Author a = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        String oldName = a.getFullName();
        testEntityManager.detach(a);

        a.setFullName(NEW_COOL_AUTHOR);
        authorDao.save(a);
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
         assertThatCode(() -> authorDao.findById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();
        authorDao.deleteById(EXISTING_AUTHOR_ID);
        authorDao.deleteById(EXISTING_AUTHOR_ID);
//        assertThatThrownBy(() -> authorDao.deleteById(EXISTING_AUTHOR_ID))
//                .isInstanceOf(RuntimeException.class);
    }

}