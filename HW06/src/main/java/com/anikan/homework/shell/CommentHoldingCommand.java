package com.anikan.homework.shell;

import com.anikan.homework.service.CommentService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;

@ShellComponent
public class CommentHoldingCommand {
    private final CommentService commentService;

    public CommentHoldingCommand(CommentService commentService) {
        this.commentService = commentService;
    }


    @ShellMethod(value = "Show all comments", key={"showallcomments", "showAllComments", "show all comments"})
    public String showAllComments(){
        StringBuilder sb = new StringBuilder();
        commentService.getAll().forEach(g -> sb.append(g.toString()).append("\n"));
        return sb.toString();
    }


}
