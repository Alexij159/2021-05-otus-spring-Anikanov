package com.anikan.homework.domain;

public class Book {
    private Long id;
    private String title;
//    private Author author;
//    private Genre genre;

    private Long authorId;
    private Long genreId;

    public Book(Long id, String title, Long authorId, Long genreId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Book(String title, Long authorId, Long genreId) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
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

//    public Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }
//
//    public Genre getGenre() {
//        return genre;
//    }
//
//    public void setGenre(Genre genre) {
//        this.genre = genre;
//    }

    @Override
    public String toString() {
        return "Id: " + id.toString()
                + ", title: " + title
                + ", authorId: " + authorId
                + ", genreId: " + genreId;
    }
}
