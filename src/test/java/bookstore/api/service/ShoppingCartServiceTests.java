package bookstore.api.service;

import bookstore.api.model.Book;
import bookstore.api.model.Role;
import bookstore.api.model.ShoppingCart;
import bookstore.api.model.User;
import bookstore.api.repository.ShoppingCartRepository;
import bookstore.api.service.impl.ShoppingCartServiceImpl;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTests {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    private User user;
    private ShoppingCart shoppingCart;
    private Book book;

    @BeforeEach
    public void init() {
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
    public void ShoppingCartService_RegisterNewShoppingCart_ReturnVoid() {
        when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class))).thenReturn(shoppingCart);

        shoppingCartService.registerNewShoppingCart(user);

        assertEquals(user, shoppingCart.getUser());
    }

    @Test
    public void ShoppingCartService_GetByUser_ReturnShoppingCart() {
        when(shoppingCartRepository.findByUser(user)).thenReturn(shoppingCart);

        assertEquals(shoppingCart, shoppingCartService.getByUser(user));
    }

    @Test
    public void ShoppingCartService_ClearShoppingCart_ReturnVoid() {
        when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class))).thenReturn(shoppingCart);

        shoppingCartService.clearShoppingCart(shoppingCart);

        assertEquals(0, shoppingCart.getBooks().size());
    }

    @Test
    public void ShoppingCartService_AddBook_ReturnVoid() {
        when(shoppingCartRepository.findByUser(user)).thenReturn(shoppingCart);
        when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class))).thenReturn(shoppingCart);

        shoppingCartService.addBook(book, user);

        assertThat(shoppingCart.getBooks().contains(book)).isTrue();
    }

    @Test
    public void ShoppingCartService_RemoveBook_ReturnVoid() {
        when(shoppingCartRepository.findByUser(user)).thenReturn(shoppingCart);
        when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class))).thenReturn(shoppingCart);

        shoppingCartService.removeBook(book, user);

        assertThat(shoppingCart.getBooks().contains(book)).isFalse();
    }
}