package bookstore.api.repository;

import bookstore.api.model.Role;
import bookstore.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_FindByEmail_ReturnUser(){
        User user = User.builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        User userByEmail = userRepository.findByEmail(user.getEmail()).get();

        assertThat(userByEmail).isNotNull();
    }


    @Test
    public void UserRepository_Save_ReturnSavedUser(){
        User user = User.builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_FindAll_ReturnAllUsers(){
        User user = User.builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();
        User user2 = User.builder()
                .username("username2")
                .email("email2")
                .password("password2")
                .role(Role.USER)
                .build();
        userRepository.save(user);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }
}
