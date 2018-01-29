package edu.shapovalov.library.service.impl;

import edu.shapovalov.library.dto.BookDto;
import edu.shapovalov.library.dto.BookFilterDto;
import edu.shapovalov.library.entity.Book;
import edu.shapovalov.library.entity.User;
import edu.shapovalov.library.repositiry.BookRepository;
import edu.shapovalov.library.repositiry.UserRepository;
import edu.shapovalov.library.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private String defaultImage;
    private String imagesPath;
    private String filePath;
    private JdbcTemplate jdbcTemplate;

    public BookServiceImpl(BookRepository bookRepository,
                           UserRepository userRepository,
                           @Value("${img.default}") String defaultImage,
                           @Value("${img.upload.path}") String imagesPath,
                           @Value("${book.upload.path}") String filePath,
                           JdbcTemplate jdbcTemplate) {
        this.defaultImage = defaultImage;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.imagesPath = imagesPath;
        this.filePath = filePath;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Book> listBooks(int page, String search, String sort) {
        Pageable pageable = new PageRequest(page - 1, 5, new Sort(sort));
        if (search.isEmpty()) {
            return bookRepository.findAll(pageable);
        } else {
            return bookRepository.findAllByNameContainsOrDescriptionContains(search, search, pageable);
        }
    }

    @Override
    public void addBook(BookDto bookDto) throws IOException {
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setDateAdded(LocalDateTime.now());

        UserDetails userDetails = UserDetails.class.cast(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username);

        book.setUser(user);

        bookRepository.save(book);

        Files.write(Paths.get(imagesPath, book.getId().toString()), bookDto.getImage().getBytes());
        Files.write(Paths.get(filePath, book.getId().toString()), bookDto.getFile().getBytes());
    }

    @Override
    public void remove(Integer id) throws IOException {
        bookRepository.delete(id);

        Files.deleteIfExists(Paths.get(imagesPath, id.toString()));
        Files.deleteIfExists(Paths.get(filePath, id.toString()));
    }

    @Override
    public byte[] image(Integer id) throws IOException {
        if (imageExists(id)) {
            return Files.readAllBytes(Paths.get(imagesPath, id.toString()));
        } else {
            return Files.readAllBytes(Paths.get(defaultImage));
        }
    }

    @Override
    public byte[] file(Integer id) throws IOException {
        return Files.readAllBytes(Paths.get(filePath, id.toString()));
    }

    @Override
    public List<Book> search(BookFilterDto filter) {
        String sql = "SELECT * FROM books WHERE ";
        sql += "(name IS NOT NULL)";
        if (filter.getName() != null && !filter.getName().trim().isEmpty()) {
            if (filter.isStrict()) {
                sql += " AND ";
            } else {
                sql += " OR ";
            }
            sql += "name LIKE '%" + filter.getName().trim() + "%'";
        }
        if (filter.getDescription() != null && !filter.getDescription().trim().isEmpty()) {
            if (filter.isStrict()) {
                sql += " AND ";
            } else {
                sql += " OR ";
            }
            sql += "description LIKE '%" + filter.getDescription().trim() + "%'";
        }
        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            if (filter.isStrict()) {
                sql += " AND ";
            } else {
                sql += " OR ";
            }
            sql += "(date_added BETWEEN " + filter.getStartDate() + " AND " + filter.getEndDate() + ")";
        }
        if (filter.getStartDate() != null && filter.getEndDate() == null) {
            if (filter.isStrict()) {
                sql += " AND ";
            } else {
                sql += " OR ";
            }
            sql += "date_added > " + filter.getStartDate();
        }
        if (filter.getStartDate() == null && filter.getEndDate() != null) {
            if (filter.isStrict()) {
                sql += " AND ";
            } else {
                sql += " OR ";
            }
            sql += "date_added < " + filter.getEndDate();
        }
        sql += " ORDER BY " + filter.getSortBy();
        List<Book> books = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Book.class));
        if (filter.isOnlyWithImages()) {
            return books
                    .stream()
                    .filter(this::imageExists)
                    .collect(Collectors.toList());
        } else {
            return books;
        }
    }

    private boolean imageExists(Book book) {
        return imageExists(book.getId());
    }
    private boolean imageExists(Integer id) {
        return Files.exists(Paths.get(imagesPath, id.toString()));
    }

    private boolean fileExists(Book book) {
        return fileExists(book.getId());
    }
    private boolean fileExists(Integer id) {
        return Files.exists(Paths.get(filePath, id.toString()));
    }
}
