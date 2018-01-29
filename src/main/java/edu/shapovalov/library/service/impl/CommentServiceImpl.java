package edu.shapovalov.library.service.impl;

import edu.shapovalov.library.dto.CommentDto;
import edu.shapovalov.library.dto.CommentWSMessage;
import edu.shapovalov.library.entity.Comment;
import edu.shapovalov.library.repositiry.BookRepository;
import edu.shapovalov.library.repositiry.CommentRepository;
import edu.shapovalov.library.service.CommentService;
import edu.shapovalov.library.service.SecurityService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Artem Shapovalov
 * @version 0.1
 * @since 06.01.2018
 */

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private BookRepository bookRepository;
    private SecurityService securityService;
    private SimpMessagingTemplate simpMessagingTemplate;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository, SecurityService securityService, SimpMessagingTemplate simpMessagingTemplate) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.securityService = securityService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void addComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setUser(securityService.getCurrentUser());
        comment.setBook(bookRepository.findOne(commentDto.getBookId()));
        commentRepository.save(comment);

        CommentWSMessage wsMessage = new CommentWSMessage();
        wsMessage.setBookName(comment.getBook().getName());
        wsMessage.setUserName(comment.getUser().getUsername());
        simpMessagingTemplate.convertAndSend("/topic/comments", wsMessage);
    }
}
