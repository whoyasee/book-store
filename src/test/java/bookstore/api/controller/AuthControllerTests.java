package bookstore.api.controller;

import bookstore.api.dto.UserDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    }

    @Test
    public void AuthController_Register_ReturnUserDTO() throws Exception {

        when(userService.save(ArgumentMatchers.any())).thenReturn(true);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email@dto.com"));
    }
}
