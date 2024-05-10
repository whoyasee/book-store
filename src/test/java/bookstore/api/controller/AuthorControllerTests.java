package bookstore.api.controller;

import bookstore.api.dto.AuthorDTO;
import bookstore.api.model.Author;
import bookstore.api.model.Book;
import bookstore.api.service.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AuthorService authorService;

    private Author author;
    private AuthorDTO authorDTO;
    private Book book;

    @BeforeEach
    public void init(){
        author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .build();
        authorDTO = AuthorDTO.builder()
                .firstName("first name dto")
                .lastName("last name dto")
                .build();
        book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();

        when(modelMapper.map(any(AuthorDTO.class), ArgumentMatchers.any())).thenReturn(author);
        when(modelMapper.map(any(Author.class), ArgumentMatchers.any())).thenReturn(authorDTO);
    }

    @Test
    public void AuthorController_FindAll_ReturnAuthorDTOs() throws Exception {

        when(authorService.findAll()).thenReturn(List.of(author));

        mockMvc.perform(get("/authors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("first name dto"));
    }

    @Test
    public void AuthorController_FindAuthorById_ReturnAuthorDTO() throws Exception {

        when(authorService.getById(1L)).thenReturn(author);

        mockMvc.perform(get("/authors/{authorId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("first name dto"));
    }

    @Test
    public void AuthorController_AddAuthor_ReturnAuthorDTO() throws Exception {

        when(authorService.save(ArgumentMatchers.any())).thenReturn(author);

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("first name dto"));
    }

    @Test
    public void AuthorController_DeleteAuthor_ReturnVoid() throws Exception{

        doNothing().when(authorService).deleteAuthor(author.getId());

        mockMvc.perform(delete("/authors/{authorId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void AuthorController_AssertBookToAuthor_ReturnAuthorDTO() throws Exception{

        when(authorService.assignBookToAuthor(author.getId(), book.getId())).thenReturn(author);

        mockMvc.perform(put("/authors/{authorId}/books/{bookId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
