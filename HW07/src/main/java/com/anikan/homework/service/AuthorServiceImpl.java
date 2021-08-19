package com.anikan.homework.service;

import com.anikan.homework.exceptions.NoSuchAuthorException;
import com.anikan.homework.dao.AuthorDao;
import com.anikan.homework.domain.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static java.util.Objects.isNull;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Author getById(Long id) {
        Author author = null;
        try {
            author = authorDao.findById(id).orElseThrow(() ->new NoSuchAuthorException("There is no author with such ID."));
        } catch (EmptyResultDataAccessException exception){
            throw new NoSuchAuthorException(exception);
        }

        return author;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll() {
        return authorDao.findAll();
    }

    @Transactional
    @Override
    public Long insertNew(Author author) {
        return authorDao.insert(author);
    }

    @Transactional
    @Override
    public Author update(Author author) {
        return authorDao.save(author);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        authorDao.deleteById(id);
    }
}
