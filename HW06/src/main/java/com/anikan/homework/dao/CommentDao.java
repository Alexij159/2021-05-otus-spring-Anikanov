package com.anikan.homework.dao;

import com.anikan.homework.domain.Comment;

import java.util.List;

public interface CommentDao {
    Comment getById(Long id);
    List<Comment> getAll();
    Long insertByBookId(Comment comment, Long bookId);
    Comment update(Comment comment);
    void deleteById(Long id);
    void deleteByBookId(Long bookId);
}
