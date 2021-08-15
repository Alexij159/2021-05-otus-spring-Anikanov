package com.anikan.homework.service;

import com.anikan.homework.Exceptions.NoSuchCommentException;
import com.anikan.homework.dao.CommentDao;
import com.anikan.homework.domain.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }


    @Override
    public Comment getById(Long id) {
        Comment comment = commentDao.getById(id);
        if (isNull(comment)){
            throw new NoSuchCommentException("There is no comment with such ID.");
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Override
    public Long insertByBookId(Comment comment, Long bookId) {
        return commentDao.insertByBookId(comment, bookId);
    }

    @Override
    public Comment update(Comment author) {
        return commentDao.update(author);
    }

    @Override
    public void deleteById(Long id) {
        commentDao.deleteById(id);
    }
}
