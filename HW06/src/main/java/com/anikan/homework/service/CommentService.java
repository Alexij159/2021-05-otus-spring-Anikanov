package com.anikan.homework.service;



import com.anikan.homework.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment getById(Long id);
    List<Comment> getAll();
    Long insertByBookId(Comment comment, Long bookId);
    Comment update(Comment author);
    void deleteById(Long id);
}
