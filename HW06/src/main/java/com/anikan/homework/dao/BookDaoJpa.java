package com.anikan.homework.dao;

import com.anikan.homework.exceptions.BookUpdateException;
import com.anikan.homework.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private final EntityManager entityManager;

    private final CommentDao commentDao;

    public BookDaoJpa(EntityManager entityManager, CommentDao commentDao) {
        this.entityManager = entityManager;
        this.commentDao = commentDao;
    }

    @Override
    public Book getById(Long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        EntityGraph<?> authorsEntityGraph = entityManager.getEntityGraph("authors-genres-entity-graph");
        query.setHint("javax.persistence.fetchgraph", authorsEntityGraph);
        return query.getResultList();
    }

    @Override
    public Long insert(Book book) {
        entityManager.persist(book);
        entityManager.flush();
        return book.getId();
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == 0){
            throw new BookUpdateException("Book should be inserted");
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(Long id) {
        commentDao.deleteByBookId(id);
        Query query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
