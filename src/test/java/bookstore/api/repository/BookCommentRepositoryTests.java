package bookstore.api.repository;

import bookstore.api.model.Book;
import bookstore.api.model.BookComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookCommentRepositoryTests{

    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void BookCommentRepository_FindById_ReturnBookComment(){
        Book book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();
        BookComment bookComment = BookComment.builder()
                .text("text")
                .createdAt(LocalDateTime.now())
                .build();


        book.addComment(bookComment);
        bookRepository.save(book);
        BookComment bookCommentById = bookCommentRepository.findById(bookComment.getId()).get();

        assertThat(bookCommentById).isNotNull();
    }
}
