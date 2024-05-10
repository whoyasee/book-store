package bookstore.api.controller;

import bookstore.api.dto.OrderDTO;
import bookstore.api.model.*;
import bookstore.api.service.OrderService;
import bookstore.api.service.ShoppingCartService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class OrderControllerTests {
    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ShoppingCartService shoppingCartService;


    private User user;
    private ShoppingCart shoppingCart;
    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(applicationContext).apply(springSecurity())
            .build();
        order = Order.builder()
                .totalPrice(BigDecimal.ZERO)
                .build();
        orderDTO = OrderDTO.builder()
                .totalPrice(BigDecimal.ZERO)
                .build();
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

        when(modelMapper.map(any(OrderDTO.class), ArgumentMatchers.any())).thenReturn(order);
        when(modelMapper.map(any(Order.class), ArgumentMatchers.any())).thenReturn(orderDTO);
    }



    @Test
    @WithMockUser(roles="USER")
    public void OrderController_ConfirmOrder_ReturnOrderDTO() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(orderService.confirmOrder(ArgumentMatchers.any())).thenReturn(order);
        when(shoppingCartService.getByUser(ArgumentMatchers.any())).thenReturn(shoppingCart);

        mockMvc.perform(post("/orders/confirm").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice").value(BigDecimal.ZERO));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void OrderController_OrderHistory_ReturnOrderDTOs() throws Exception {

        when(userService.getUserByEmail(ArgumentMatchers.any())).thenReturn(user);
        when(orderService.ordersHistory(ArgumentMatchers.any())).thenReturn(List.of(order));

        mockMvc.perform(get("/orders").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].totalPrice").value(BigDecimal.ZERO));
    }
}
