package edu.shapovalov.library.service;

import edu.shapovalov.library.dto.BookDto;
import edu.shapovalov.library.dto.BookFilterDto;
import edu.shapovalov.library.entity.Book;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

public interface BookService {
    Page<Book> listBooks(int page, String search, String sort);

    void addBook(BookDto bookDto) throws IOException;

    void remove(Integer id);

    byte[] image(Integer id) throws IOException;

    byte[] file(Integer id) throws IOException;

    List<Book> search(BookFilterDto filter);
}
