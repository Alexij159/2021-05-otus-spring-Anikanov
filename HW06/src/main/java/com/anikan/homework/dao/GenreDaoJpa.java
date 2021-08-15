package com.anikan.homework.dao;

import com.anikan.homework.Exceptions.GenreUpdateException;
import com.anikan.homework.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public GenreDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Genre getById(Long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Long insert(Genre genre) {
        entityManager.persist(genre);
        return genre.getId();
    }

    @Override
    public Genre update(Genre genre) {
        if (isNull(genre.getId()) || genre.getId() == 0) {
            throw new GenreUpdateException("Genre should be inserted");
        }
        return entityManager.merge(genre);
    }

    @Override
    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
