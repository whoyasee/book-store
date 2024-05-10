package bookstore.api.repository;

import bookstore.api.model.Role;
import bookstore.api.model.User;
import bookstore.api.model.WishList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishListRepositoryTests {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void ShoppingCartRepository_Save_ReturnSavedShoppingCart(){
        User user = User.builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        WishList wishList = WishList.builder()
                .user(user)
                .build();

        WishList savedWishList = wishListRepository.save(wishList);

        assertThat(savedWishList).isNotNull();
        assertThat(savedWishList.getId()).isGreaterThan(0);
    }

    @Test
    public void ShoppingCartRepository_findByUser_ReturnSavedShoppingCart() {
        User user = User.builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        WishList wishList = WishList.builder()
                .user(user)
                .build();

        wishListRepository.save(wishList);

        WishList wishListByUser = wishListRepository.findByUser(wishList.getUser());

        assertThat(wishListByUser).isNotNull();
    }
}