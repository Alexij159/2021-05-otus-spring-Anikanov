package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorDao extends CrudRepository<Author, Long> {
//    Author getById(Long id);
    List<Author> findAll();
    Long insert(Author author);
//    Author update(Author author);
//    void deleteById(Long id);
}
