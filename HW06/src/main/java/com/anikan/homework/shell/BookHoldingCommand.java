package com.anikan.homework.shell;

import com.anikan.homework.dto.BookDto;
import com.anikan.homework.exceptions.NoSuchAuthorException;
import com.anikan.homework.exceptions.NoSuchBookException;
import com.anikan.homework.exceptions.NoSuchGenreException;
import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Genre;
import com.anikan.homework.service.AuthorService;
import com.anikan.homework.service.BookService;
import com.anikan.homework.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;

import static java.util.Objects.isNull;

@ShellComponent
public class BookHoldingCommand {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    public BookHoldingCommand(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }


    @ShellMethod(value = "Show all books", key = {"showAllBooks", "showallbooks"})
    public String showAllBooks() {
        StringBuilder sb = new StringBuilder();
        bookService.getAll().forEach(b -> sb.append(b.toString()).append("\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Show book by id", key = {"showBookById", "showbookbyid"})
    public String showBookById(Long id){
        BookDto b = null;
        try {
            b = bookService.getById(id);
        }  catch (NoSuchBookException ex){
            return "There is no such book.";
        }
        return b.toString();
    }

    @ShellMethod(value = "Create book", key = {"createBook", "create book"})
    public String createBook(String title, Long authorId, Long genreId){
        try {
            Genre g = genreService.getById(genreId);
            Author a = authorService.getById(authorId);
            Book b = new Book(title,a,g);
            Long bookId = bookService.insertNew(b);
            return "We created the book with ID: " + bookId.toString();
        } catch (NoSuchGenreException ex){
            return "There is no genre with such ID.";
        } catch (NoSuchAuthorException ex){
            return "There is no author with such ID.";
        }
    }


    @ShellMethod(value = "Update book", key = {"updateBook", "update book"})
    public String updateBook(Long id, String title, Long authorId, Long genreId){
        try {
            BookDto book = bookService.getById(id);
            if (isNull(book)){
                return "There is no book with such ID";
            }
            Genre g = genreService.getById(genreId);
            Author a = authorService.getById(authorId);
            book.setTitle(title);
            book.setAuthor(a);
            book.setGenre(g);
            bookService.update(book);
            return "Book was updated";
        } catch (NoSuchGenreException ex) {
            return "There is no genre with such ID.";
        } catch (NoSuchAuthorException ex){
            return "There is no author with such ID.";
        }
    }

    @ShellMethod(value = "Delete book", key = {"deleteBook", "delete book"})
    public String deleteBook(Long id){
        bookService.delete(id);
        return "Book was deleted";
    }


}
