package bookstore.api.service;

import bookstore.api.model.Author;
import bookstore.api.model.Book;
import bookstore.api.repository.AuthorRepository;
import bookstore.api.repository.BookRepository;
import bookstore.api.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Assertions;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTests {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void AuthorService_GetById_ReturnAuthor() {
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .build();

        when(authorRepository.findById(1L)).thenReturn(Optional.ofNullable(author));

        Assertions.assertEquals(author, authorService.getById((1L)));
    }

    @Test
    public void AuthorService_FindAll_ReturnAuthors() {
        when(authorRepository.findAllFetchBooks()).thenReturn(List.of(Author.builder()
                .firstName("first name")
                .lastName("last name").build(), Author.builder()
                .firstName("first name2")
                .lastName("last name2").build()));

        Assertions.assertEquals(2, authorService.findAll().size());
    }

    @Test
    public void AuthorService_SaveAuthor_ReturnSavedAuthor() {
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .build();

        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(author);

        Assertions.assertEquals(author, authorService.save(author));
    }

    @Test
    void AuthorService_DeleteAuthor_ReturnVoid() {
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .build();
        authorService.deleteAuthor(author.getId());

        verify(authorRepository, times(1)).deleteById(author.getId());
    }

    @Test
    void AuthorService_AssignBookToAuthor() {
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .books(new HashSet<>())
                .build();
        Book book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();
        when(authorRepository.findByIdFetchBooks(1L)).thenReturn(Optional.ofNullable(author));
        when(bookRepository.findByIdFetchComments(1L)).thenReturn(Optional.ofNullable(book));
        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(author);

        authorService.assignBookToAuthor(1L, 1L);
        assertThat(author.getBooks().contains(book)).isTrue();
    }
}