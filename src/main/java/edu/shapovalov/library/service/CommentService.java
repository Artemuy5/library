package edu.shapovalov.library.service;

import edu.shapovalov.library.dto.CommentDto;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

public interface CommentService {
    void addComment(CommentDto commentDto);
}
