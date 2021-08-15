package com.anikan.homework.service;

import com.anikan.homework.Exceptions.NoSuchAuthorException;
import com.anikan.homework.dao.AuthorDao;
import com.anikan.homework.domain.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Author getById(Long id) {
        Author author = null;
        try {
            author = authorDao.getById(id);
            if (isNull(author)){
                throw new NoSuchAuthorException("There is no author with such ID.");
            }

        } catch (EmptyResultDataAccessException exception){
            throw new NoSuchAuthorException(exception);
        }

        return author;
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Long insertNew(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public Author update(Author author) {
        return authorDao.update(author);
    }

    @Override
    public void deleteById(Long id) {
        authorDao.deleteById(id);
    }
}
