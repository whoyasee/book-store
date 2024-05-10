package bookstore.api.repository;

import bookstore.api.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void BookRepository_Save_ReturnSavedBook(){
        Book book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isGreaterThan(0);
    }

    @Test
    public void BookRepository_FindById_ReturnBook(){
        Book book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();
        bookRepository.save(book);

        Book bookById = bookRepository.findById(book.getId()).get();

        assertThat(bookById).isNotNull();
    }

    @Test
    public void BookRepository_DeleteBook_ReturnBookIsEmpty(){
        Book book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();

        bookRepository.save(book);


        bookRepository.delete(book);
        Optional<Book> bookById = bookRepository.findById(book.getId());


        assertThat(bookById).isEmpty();
    }
}