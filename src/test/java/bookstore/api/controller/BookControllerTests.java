package bookstore.api.controller;

import bookstore.api.dto.AuthorDTO;
import bookstore.api.dto.BookCommentDTO;
import bookstore.api.dto.BookDTO;
import bookstore.api.model.Author;
import bookstore.api.model.Book;
import bookstore.api.model.BookComment;
import bookstore.api.service.AuthorService;
import bookstore.api.service.BookCommentService;
import bookstore.api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookCommentService bookCommentService;

    private Book book;
    private BookDTO bookDTO;

    private BookComment bookComment;
    private BookCommentDTO bookCommentDTO;

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
        bookDTO = BookDTO.builder()
                .name("book dto")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .bookComments(new ArrayList<>())
                .build();
        bookComment = BookComment.builder()
                .text("text")
                .createdAt(LocalDateTime.now())
                .build();
        bookCommentDTO = BookCommentDTO.builder()
                .text("text dto")
                .createdAt(LocalDateTime.now())
                .build();

        when(modelMapper.map(any(BookDTO.class), ArgumentMatchers.any())).thenReturn(book);
        when(modelMapper.map(any(Book.class), ArgumentMatchers.any())).thenReturn(bookDTO);
        when(modelMapper.map(any(BookCommentDTO.class), ArgumentMatchers.any())).thenReturn(bookComment);
        when(modelMapper.map(any(BookComment.class), ArgumentMatchers.any())).thenReturn(bookCommentDTO);
    }

    @Test
    public void BookController_FindAll_ReturnBookDTOs() throws Exception {

        when(bookService.findAll()).thenReturn(List.of(book));

        mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("book dto"));
    }

    @Test
    public void BookController_FindBookById_ReturnBookDTO() throws Exception {

        when(bookService.getById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("book dto"));
    }

    @Test
    public void BookController_AddBook_ReturnBookDTO() throws Exception {

        when(bookService.save(ArgumentMatchers.any())).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("book dto"));
    }

    @Test
    public void BookController_DeleteBook_ReturnVoid() throws Exception{

        doNothing().when(bookService).deleteBook(book.getId());

        mockMvc.perform(delete("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void BookController_GetComments_ReturnCommentDTOs() throws Exception {

        when(bookService.getById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/{id}/comments", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void BookController_AddComment_ReturnCommentDTO() throws Exception {

        when(bookService.getById(1L)).thenReturn(book);
        when(bookService.save(ArgumentMatchers.any())).thenReturn(book);

        mockMvc.perform(post("/books/{id}/comments", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCommentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("text dto"));
    }

    @Test
    public void BookController_DeleteComment_ReturnVoid() throws Exception{

        when(bookService.getById(1L)).thenReturn(book);
        when(bookCommentService.getById(1L)).thenReturn(bookComment);
        when(bookService.save(ArgumentMatchers.any())).thenReturn(book);

        mockMvc.perform(delete("/books/{id}/comments/{commentId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
