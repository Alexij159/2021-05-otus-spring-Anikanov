package com.anikan.homework.shell;

import com.anikan.homework.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GenreHoldingCommand {
    private final GenreService genreService;

    public GenreHoldingCommand(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod(value = "Show all genres", key ={"showAllGenres"})
    public String showAllGenres() {
        StringBuilder sb = new StringBuilder();
        genreService.getAll().forEach(g -> sb.append(g.toString()).append("\n"));
        return sb.toString();
    }
}