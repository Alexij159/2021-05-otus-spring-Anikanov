package com.anikan.homework.service;

import com.anikan.homework.Exceptions.NoSuchGenreException;
import com.anikan.homework.dao.GenreDao;
import com.anikan.homework.domain.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class GenreServiceImpl implements GenreService{
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre getById(Long id) {
        Genre genre = null;
        try {
            genre = genreDao.getById(id);
            if (isNull(genre)){
                throw new NoSuchGenreException("There is no genre with such ID.");
            }
        } catch (EmptyResultDataAccessException exception){
            throw new NoSuchGenreException(exception);
        }

        return genre;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Long insertNew(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return genreDao.update(genre);
    }
}
