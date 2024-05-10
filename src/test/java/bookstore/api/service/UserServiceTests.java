package bookstore.api.service;

import bookstore.api.model.Role;
import bookstore.api.model.User;
import bookstore.api.repository.UserRepository;
import bookstore.api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void init() {
        user = User.builder()
                .username("username")
                .email("email")
                .password("password")
                .role(Role.USER)
                .build();
    }

    @Test
    public void findAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertEquals(1, userService.findAll().size());
    }


    @Test
    public void getUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));

        assertEquals(user, userService.getUserByEmail(user.getEmail()));
    }
}