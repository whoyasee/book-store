package bookstore.api.service;

import bookstore.api.model.*;
import bookstore.api.repository.WishListRepository;
import bookstore.api.service.impl.WishListServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishListServiceTests {
    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishListServiceImpl wishListService;
    private User user;
    private WishList wishList;
    private Book book;

    @BeforeEach
    public void init() {
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
    public void WishListService_RegisterNewWishList_ReturnVoid() {
        when(wishListRepository.save(Mockito.any(WishList.class))).thenReturn(wishList);

        wishListService.registerNewWishList(user);

        assertEquals(user, wishList.getUser());
    }

    @Test
    public void WishListService_GetByUser_ReturnWishList() {
        when(wishListRepository.findByUser(user)).thenReturn(wishList);

        assertEquals(wishList, wishListService.getByUser(user));
    }

    @Test
    public void WishListService_ClearWishList_ReturnVoid() {
        when(wishListRepository.save(Mockito.any(WishList.class))).thenReturn(wishList);

        wishListService.clearWishList(wishList);

        assertEquals(0, wishList.getBooks().size());
    }

    @Test
    public void WishListService_AddBook_ReturnVoid() {
        when(wishListRepository.findByUser(user)).thenReturn(wishList);
        when(wishListRepository.save(Mockito.any(WishList.class))).thenReturn(wishList);

        wishListService.addBook(book, user);

        assertThat(wishList.getBooks().contains(book)).isTrue();
    }

    @Test
    public void WishListService_RemoveBook_ReturnVoid() {
        when(wishListRepository.findByUser(user)).thenReturn(wishList);
        when(wishListRepository.save(Mockito.any(WishList.class))).thenReturn(wishList);

        wishListService.removeBook(book, user);

        assertThat(wishList.getBooks().contains(book)).isFalse();
    }
}