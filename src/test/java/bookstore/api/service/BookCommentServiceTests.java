package bookstore.api.service;

import bookstore.api.model.Author;
import bookstore.api.model.Book;
import bookstore.api.model.BookComment;
import bookstore.api.repository.BookCommentRepository;
import bookstore.api.repository.BookRepository;
import bookstore.api.service.impl.BookCommentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookCommentServiceTests {

    @Mock
    private BookCommentRepository bookCommentRepository;
    @InjectMocks
    private BookCommentServiceImpl bookCommentService;

    @Test
    void BookCommentService_GetById_ReturnBookComment() {
        BookComment bookComment = BookComment.builder()
                .text("text")
                .createdAt(LocalDateTime.now())
                .build();

        when(bookCommentRepository.findById(1L)).thenReturn(Optional.ofNullable(bookComment));

        Assertions.assertEquals(bookComment, bookCommentService.getById((1L)));
    }
}