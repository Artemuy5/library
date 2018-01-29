package edu.shapovalov.library.repositiry;

import edu.shapovalov.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 05.01.2018.
 */

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Page<Book> findAllByNameContainsOrDescriptionContains(String name, String desc, Pageable pageable);
}
