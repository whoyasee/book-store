package bookstore.api.service;

import bookstore.api.model.Book;
import bookstore.api.repository.BookRepository;
import bookstore.api.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    public void init(){
        book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();
    }
    @Test
    void getById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));

        assertEquals(book, bookService.getById(1L));
    }

    @Test
    void BookService_findAll_ReturnBooks() {
        when(bookRepository.findAllFetchComments()).thenReturn(List.of(book));
        assertEquals(1, bookService.findAll().size());
    }

    @Test
    void BookService_deleteBook_ReturnVoid() {
        bookService.deleteBook(book.getId());

        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    public void BookService_Save_ReturnSavedBook() {

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        assertEquals(book, bookService.save(book));
    }
}