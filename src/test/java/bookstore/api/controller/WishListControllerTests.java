package bookstore.api.controller;

import bookstore.api.dto.ShoppingCartDTO;
import bookstore.api.dto.WishListDTO;
import bookstore.api.model.*;
import bookstore.api.service.BookService;
import bookstore.api.service.ShoppingCartService;
import bookstore.api.service.UserService;
import bookstore.api.service.WishListService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishListController.class)
@ExtendWith(MockitoExtension.class)
public class WishListControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @MockBean
    private WishListService wishListService;

    private WishList wishList;
    private WishListDTO wishListDTO;
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
        wishList = WishList.builder()
                .user(user)
                .books(new HashSet<>())
                .build();
        wishListDTO = WishListDTO.builder()
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

        when(modelMapper.map(any(WishListDTO.class), ArgumentMatchers.any())).thenReturn(wishList);
        when(modelMapper.map(any(WishList.class), ArgumentMatchers.any())).thenReturn(wishListDTO);
    }

    @Test
    @WithMockUser(roles="USER")
    public void WishListController_GetByUser_ReturnWishListDTO() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(wishListService.getByUser(user)).thenReturn(wishList);

        mockMvc.perform(get("/wish-lists/by-user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void WishListController_AddToList_ReturnVoid() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(bookService.getById(1L)).thenReturn(book);
        doNothing().when(wishListService).addBook(ArgumentMatchers.any(), ArgumentMatchers.any());

        mockMvc.perform(put("/wish-lists/books").param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void WishListController_RemoveFromList_ReturnVoid() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(bookService.getById(1L)).thenReturn(book);
        doNothing().when(wishListService).removeBook(ArgumentMatchers.any(), ArgumentMatchers.any());

        mockMvc.perform(delete("/wish-lists/books").param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void ShoppingCartController_ClearCart_ReturnVoid() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(wishListService.getByUser(any())).thenReturn(wishList);
        doNothing().when(wishListService).clearWishList(ArgumentMatchers.any());

        mockMvc.perform(delete("/wish-lists/books").param("bookId", "1")
                        .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());
    }
}
