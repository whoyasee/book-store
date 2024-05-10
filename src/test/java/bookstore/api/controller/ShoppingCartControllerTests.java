package bookstore.api.controller;

import bookstore.api.dto.ShoppingCartDTO;
import bookstore.api.model.*;
import bookstore.api.service.BookService;
import bookstore.api.service.ShoppingCartService;
import bookstore.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingCartController.class)
@ExtendWith(MockitoExtension.class)
public class ShoppingCartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @MockBean
    private ShoppingCartService shoppingCartService;

    private ShoppingCart shoppingCart;
    private ShoppingCartDTO shoppingCartDTO;
    private User user;
    private Book book;

    @BeforeEach
    public void init(){
        user = User.builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();
        shoppingCart = ShoppingCart.builder()
                .user(user)
                .books(new HashSet<>())
                .build();
        shoppingCartDTO = ShoppingCartDTO.builder()
                .books(new HashSet<>())
                .build();
        book = Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .authors(new HashSet<>())
                .build();
        when(modelMapper.map(any(ShoppingCartDTO.class), ArgumentMatchers.any())).thenReturn(shoppingCart);
        when(modelMapper.map(any(ShoppingCart.class), ArgumentMatchers.any())).thenReturn(shoppingCartDTO);
    }

    @Test
    @WithMockUser(roles="USER")
    public void ShoppingCartController_GetByUser_ReturnShoppingCartDTO() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(shoppingCartService.getByUser(user)).thenReturn(shoppingCart);

        mockMvc.perform(get("/shopping-carts/by-user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void ShoppingCartController_AddToCart_ReturnVoid() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(bookService.getById(1L)).thenReturn(book);
        doNothing().when(shoppingCartService).addBook(ArgumentMatchers.any(), ArgumentMatchers.any());

        mockMvc.perform(put("/shopping-carts/books").param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void ShoppingCartController_RemoveFromCart_ReturnVoid() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(bookService.getById(1L)).thenReturn(book);
        doNothing().when(shoppingCartService).removeBook(ArgumentMatchers.any(), ArgumentMatchers.any());

        mockMvc.perform(delete("/shopping-carts/books").param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void ShoppingCartController_ClearCart_ReturnVoid() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(shoppingCartService.getByUser(any())).thenReturn(shoppingCart);
        doNothing().when(shoppingCartService).clearShoppingCart(ArgumentMatchers.any());

        mockMvc.perform(delete("/shopping-carts/books").param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());
    }

}
