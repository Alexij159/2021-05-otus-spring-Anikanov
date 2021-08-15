package com.anikan.homework.domain;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "bookid")
    private Book book;

    public Comment() {
    }

    public Comment(Long id, String message, Book book) {
        this.id = id;
        this.message = message;
        this.book = book;
    }

    public Comment(String message) {
        this.message = message;
    }

    public Comment(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, message: %s, book: %d", id, message, book == null ? 0:book.getId());
    }
}
