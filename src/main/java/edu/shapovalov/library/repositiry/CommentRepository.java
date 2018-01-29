package edu.shapovalov.library.repositiry;

import edu.shapovalov.library.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
