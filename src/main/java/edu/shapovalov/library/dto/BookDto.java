package edu.shapovalov.library.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

@Data
public class BookDto {
    private String name;
    private String author;
    private String description;
    private MultipartFile image;
    private MultipartFile file;
}
