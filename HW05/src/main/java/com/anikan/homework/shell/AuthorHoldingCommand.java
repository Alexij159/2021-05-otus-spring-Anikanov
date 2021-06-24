package com.anikan.homework.shell;

import com.anikan.homework.service.AuthorService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AuthorHoldingCommand {
    private final AuthorService authorService;


    public AuthorHoldingCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "Show all authors", key = {"showAllAuthors"})
    public String showAllAuthors() {
        StringBuilder sb = new StringBuilder();
        authorService.getAll().forEach(a -> sb.append(a.toString()).append("\n"));
        return sb.toString();
    }
}
