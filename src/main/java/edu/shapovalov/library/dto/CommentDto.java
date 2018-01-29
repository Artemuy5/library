package edu.shapovalov.library.dto;

import lombok.Data;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Data
public class CommentDto {
    private Integer bookId;
    private String text;
}
