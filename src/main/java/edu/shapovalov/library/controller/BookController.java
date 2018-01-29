package edu.shapovalov.library.controller;

import edu.shapovalov.library.dto.BookDto;
import edu.shapovalov.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Controller
//все запросы, которые начинаются на /book, идут сюда
@RequestMapping("/book")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //по GET запросу показываем форму добавления
    @GetMapping("/add")
    public String bookForm(Model model) {
        model.addAttribute("book", new BookDto());
        return "book";
    }

    //по POST запросу обрабатываем форму и сохраняем
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") BookDto bookDto) throws IOException {
        bookService.addBook(bookDto);
        return "redirect:/";
    }

    //удаляем book по ID
    @GetMapping("/{id}/remove")
    public String remove(@PathVariable Integer id) {
        bookService.remove(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> image(@PathVariable Integer id) throws IOException {
        return ResponseEntity.ok(bookService.image(id));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> file(@PathVariable Integer id) throws IOException {
        return ResponseEntity.ok(bookService.file(id));
    }
}
