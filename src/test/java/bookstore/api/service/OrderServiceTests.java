package bookstore.api.service;

import bookstore.api.model.*;
import bookstore.api.repository.OrderRepository;
import bookstore.api.service.impl.OrderServiceImpl;
import bookstore.api.service.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShoppingCartServiceImpl shoppingCartService;
    @InjectMocks
    private OrderServiceImpl orderService;


    private User user;
    private ShoppingCart shoppingCart;
    private Order order;

    @BeforeEach
    public void init() {
        order = Order.builder()
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
    }

    @Test
    public void ordersHistory() {
        when(orderRepository.ordersHistory(user)).thenReturn(List.of(order));

        assertEquals(1, orderService.ordersHistory(user).size());
    }

    @Test
    public void OrderService_ConfirmOrder_ReturnOrder(){
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        orderService.confirmOrder(shoppingCart);

        assertEquals(orderService.confirmOrder(shoppingCart).getUser(), shoppingCart.getUser());
        assertThat(shoppingCart.getBooks().size() == 0).isTrue();
    }
}