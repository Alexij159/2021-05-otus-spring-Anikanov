package com.anikan.homework.service;

import com.anikan.homework.Exceptions.NoSuchAuthorException;
import com.anikan.homework.Exceptions.NoSuchBookException;
import com.anikan.homework.dao.AuthorDao;
import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean update(Author author) {
        return update(author);
    }

    @Override
    public void deleteById(Long id) {
        authorDao.deleteById(id);
    }
}
