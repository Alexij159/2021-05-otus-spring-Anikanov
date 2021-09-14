package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import com.anikan.homework.exceptions.AuthorUpdateException;
import com.anikan.homework.exceptions.CommentUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static java.util.Objects.isNull;

@Repository
public class AuthorDaoImpl implements AuthorDaoCustom{
    @Autowired
    private AuthorDao authorDao;


    @Override
    public Author update(Author author) {
        if(isNull(author.getId()) || author.getId() == 0){
            throw new AuthorUpdateException("Author should be inserted");
        }
        return authorDao.save(author);
    }
}
