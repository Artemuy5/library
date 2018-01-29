package edu.shapovalov.library.controller;

import edu.shapovalov.library.dto.BookFilterDto;
import edu.shapovalov.library.entity.Book;
import edu.shapovalov.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Controller
public class IndexController {
    private BookService bookService;

    public IndexController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name="search", defaultValue = "") String search,
                        @RequestParam(name="sort", defaultValue = "dateAdded") String sort) {
        Page<Book> books = bookService.listBooks(page, search.trim(), sort);
        model.addAttribute("books", books.getContent());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("filter", new BookFilterDto());
        return "index";
    }
}
