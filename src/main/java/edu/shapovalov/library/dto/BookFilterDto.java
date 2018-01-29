package edu.shapovalov.library.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Data
public class BookFilterDto {
    private String name;
    private String author;
    private String description;
    private Date startDate;
    private Date endDate = new Date();
    private String sortBy = "name";
    private boolean onlyWithImages = false;
    private boolean strict = true;

    public boolean isOnlyWithImages() {
        return onlyWithImages;
    }
    public boolean isStrict() {
        return strict;
    }
}
