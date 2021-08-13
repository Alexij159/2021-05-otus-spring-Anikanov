package com.anikan.homework.dao;

import com.anikan.homework.Exceptions.NoSuchAuthorException;
import com.anikan.homework.domain.Author;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorDaoJpa implements AuthorDao{
    @PersistenceContext
    private final EntityManager entityManager;

    public AuthorDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Author getById(Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Long insert(Author author) {
        entityManager.persist(author);
        return author.getId();
    }

    @Override
    public Author update(Author author) {
        if (author.getId() == 0) {
            throw new NoSuchAuthorException("Author should be inserted");
        }
        return entityManager.merge(author);
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
