package bookstore.api.repository;

import bookstore.api.model.Role;
import bookstore.api.model.ShoppingCart;
import bookstore.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTests {


    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
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
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .user(user)
                .build();

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        assertThat(savedShoppingCart).isNotNull();
        assertThat(savedShoppingCart.getId()).isGreaterThan(0);
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
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .user(user)
                .build();
        shoppingCartRepository.save(shoppingCart);

        ShoppingCart shoppingCartByUser = shoppingCartRepository.findByUser(shoppingCart.getUser());

        assertThat(shoppingCartByUser).isNotNull();
    }
}