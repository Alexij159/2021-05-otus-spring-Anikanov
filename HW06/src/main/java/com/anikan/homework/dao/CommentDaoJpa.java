package com.anikan.homework.dao;

import com.anikan.homework.Exceptions.CommentUpdateException;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
public class CommentDaoJpa implements CommentDao{
    @PersistenceContext
    private final EntityManager entityManager;



    public CommentDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Comment getById(Long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public Long insertByBookId(Comment comment, Long bookId) {
        Book book = entityManager.find(Book.class, bookId);
        comment.setBook(book);
        entityManager.persist(comment);
        return comment.getId();
    }

    @Override
    public Comment update(Comment comment) {
        if(isNull(comment.getId()) || comment.getId() == 0){
            throw new CommentUpdateException("Comment should be inserted");
        }
        return entityManager.merge(comment);
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteByBookId(Long bookId) {
        Query query = entityManager.createQuery("delete from Comment c where c.book.id = :id");
        query.setParameter("id", bookId);
        query.executeUpdate();
    }
}
