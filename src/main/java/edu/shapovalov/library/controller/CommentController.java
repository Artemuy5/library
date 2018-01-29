package edu.shapovalov.library.controller;

import edu.shapovalov.library.dto.CommentDto;
import edu.shapovalov.library.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Controller
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void add(@RequestBody CommentDto commentDto) {
        commentService.addComment(commentDto);
    }
}
