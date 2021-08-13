package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJdbcTest {
    private static final long EXISTING_BOOK_ID = 1L;
    private static final String NEW_TITLE = "Поэмаololo";

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private BookDaoJpa bookDao;

    private final Genre g1 = new Genre(1L,"поэма");
    private final Genre g2 = new Genre(2L,"стих");
    private final Author a1 = new Author(1L, "Пушкин Александр Сергеевич", "Пушкин А.С.", LocalDate.of(1799, 6, 06));
    private final Author a2 = new Author(2L, "Есенин Сергей Александрович", "Есенин С.А.", LocalDate.of(1895, 9, 21));

    @Test
    void getByIdNormalWork() {
        Book b = new Book(1L, "Поэма1", a2, g1);
        assertThat(bookDao.getById(EXISTING_BOOK_ID)).usingRecursiveComparison().isEqualTo(b);
    }

    @Test
    void getByIdShouldReturnNull() {
        assertThat(bookDao.getById(1000L)).isNull();
    }


    @Test
    void getAllNormalWork() {

        List<Book> books = List.of(new Book(1L, "Поэма1", a2, g1),
                new Book(2L, "Поэма2", a1, g1),
                new Book(3L, "Стих1", a1, g2),
                new Book(4L, "Стих2", a2, g2));
        assertThat(bookDao.getAll()).usingRecursiveComparison().isEqualTo(books);
    }

    @Test
    void insertNormalWork() {
        Book b = new Book( "Поэма1", a2, g1);
        long id = bookDao.insert(b);
        assertThat(bookDao.getById(id)).usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(testEntityManager.find(Book.class, id));
    }


    @Test
    void updateShouldThrowExceptionByAuthor(){
        Book book =new Book( EXISTING_BOOK_ID,"Поэмаololo", new Author(4L,"","",null), g1);
        assertThatThrownBy(() -> bookDao.update(book)).isInstanceOf(PersistenceException.class);
    }

    @Test
    void updateNormalWork() {
//        Book book =new Book( 1L,NEW_NAME, a1, g1);
        Book book = testEntityManager.find(Book.class, EXISTING_BOOK_ID);
        String oldTitle = book.getTitle();
        testEntityManager.detach(book);

        book.setTitle(NEW_TITLE);
        bookDao.update(book);
        Book updatedBook = testEntityManager.find(Book.class, EXISTING_BOOK_ID);
        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(NEW_TITLE);
    }

    @Test
    void deleteByIdNormalWork() {
        Book b = testEntityManager.find(Book.class, EXISTING_BOOK_ID);
        assertThat(b).isNotNull();
        testEntityManager.detach(b);

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThat(testEntityManager.find(Book.class, EXISTING_BOOK_ID))
                .isNull();
    }
}