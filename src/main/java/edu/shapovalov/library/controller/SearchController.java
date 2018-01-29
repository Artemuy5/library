package edu.shapovalov.library.controller;

import edu.shapovalov.library.dto.BookFilterDto;
import edu.shapovalov.library.service.BookService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Controller
public class SearchController {
    private BookService bookService;

    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }

    @PostMapping("/search")
    public String search(Model model, @ModelAttribute("filter") BookFilterDto filter) {
        model.addAttribute("filter", filter);
        model.addAttribute("books", bookService.search(filter));
        return "search";
    }
}
