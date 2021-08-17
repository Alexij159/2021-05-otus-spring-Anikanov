package com.anikan.homework.dto;

import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Comment;
import com.anikan.homework.domain.Genre;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

public class BookDto {
    private Long id;
    private String title;
    private Author author;
    private Genre genre;
    private List<Comment> comments;

    public BookDto(Long id, String title, Author author, Genre genre, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.comments = book.getComments();
        comments.size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Id: " + id.toString()
                + ", title: " + title
                + ", author: " + author.getShortName()
                + ", genre: " + genre.getName()
                + ", comments: ");
        if (comments != null){
            comments.forEach(c -> sb.append(c.getMessage()).append("; "));
        }
        return sb.toString();
    }
}
