package bookstore.api.controller;

import bookstore.api.dto.BookDTO;
import bookstore.api.dto.UserDTO;
import bookstore.api.model.Book;
import bookstore.api.model.Role;
import bookstore.api.model.User;
import bookstore.api.repository.UserRepository;
import bookstore.api.service.UserService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void init(){
        user = User.builder()
                .username("username")
                .email("email@gmail.com")
                .password("password")
                .role(Role.USER)
                .build();
        userDTO = UserDTO.builder()
                .username("username dto")
                .email("email@dto.com")
                .password("password dto")
                .matchPassword("password dto")
                .build();

        when(modelMapper.map(any(UserDTO.class), ArgumentMatchers.any())).thenReturn(user);
        when(modelMapper.map(any(User.class), ArgumentMatchers.any())).thenReturn(userDTO);
    }

    @Test
    public void UserController_GetUserByEmail_ReturnUserDTO() throws Exception {

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        mockMvc.perform(get("/users/by-email").param("email", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email@dto.com"));
    }

    @Test
    public void UserController_FindAll_ReturnUserDTOs() throws Exception {

        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("email@dto.com"));
    }

}
