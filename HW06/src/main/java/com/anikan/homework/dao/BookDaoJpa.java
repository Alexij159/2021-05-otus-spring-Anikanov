package com.anikan.homework.dao;

import com.anikan.homework.Exceptions.NoSuchBookException;
import com.anikan.homework.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;
@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public BookDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Book getById(Long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public Long insert(Book book) {
        entityManager.persist(book);
        return book.getId();
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == 0){
            throw new NoSuchBookException("Book should be inserted");
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
