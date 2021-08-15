package com.anikan.homework.dao;

import com.anikan.homework.Exceptions.CommentUpdateException;
import com.anikan.homework.Exceptions.NoSuchAuthorException;
import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({CommentDaoJpa.class, BookDaoJpa.class})
public class CommentDaoJpaTest {
    @Autowired
    private CommentDaoJpa commentDao;

    @Autowired
    TestEntityManager testEntityManager;

    private static final long EXISTING_COMMENT_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 12;
    private static final long BOOK_ID = 1L;
    private static final String NEW_COOL_MESSAGE = "New Cool";

    @Test
    void getByIdNormalWork() {
        Comment c = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(commentDao.getById(EXISTING_COMMENT_ID)).usingRecursiveComparison().isEqualTo(c);
    }

    @Test
    void getByIdShouldReturnNull(){
        assertThat(commentDao.getById(300L)).isNull();
    }

    @Test
    void getAllNormalWork() {
        List<Comment> comments = commentDao.getAll();
        assertThat(comments).hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(c -> !c.getMessage().equals(""))
                .allMatch(c -> c.getBook() != null);
    }

    @Test
    void insertByBookIdNormalWork() {
        Comment comment = new Comment("ololo");
        long id = commentDao.insertByBookId(comment, BOOK_ID);
        comment.setBook(testEntityManager.find(Book.class, BOOK_ID));

        assertThat(testEntityManager.find(Comment.class, id)).usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(comment);
    }


    @Test
    void updateNormalWork() {

        Comment c = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);
        String oldMes = c.getMessage();
        testEntityManager.detach(c);

        c.setMessage(NEW_COOL_MESSAGE);
        commentDao.update(c);
        Comment updatedComment = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(updatedComment.getMessage()).isNotEqualTo(oldMes).isEqualTo(NEW_COOL_MESSAGE);
    }

    @Test
    void updateShouldThrowException() {
        Comment c = new Comment("asd");
        assertThrows(CommentUpdateException.class,() -> commentDao.update(c));
    }



    @Test
    void deleteByBookIdNormalWork() {
        Book book = testEntityManager.find(Book.class, BOOK_ID);
        assertThat(book.getComments().size()).isGreaterThan(0);
        testEntityManager.detach(book);

        commentDao.deleteByBookId(BOOK_ID);
        book =  testEntityManager.find(Book.class, BOOK_ID);

        assertThat(book.getComments()).isEmpty();
    }
}
