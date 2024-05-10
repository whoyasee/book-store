package bookstore.api.controller;

import bookstore.api.dto.OrderDTO;
import bookstore.api.model.Order;
import bookstore.api.model.User;
import bookstore.api.service.OrderService;
import bookstore.api.service.ShoppingCartService;
import bookstore.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, ShoppingCartService shoppingCartService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/confirm")
    public ResponseEntity<OrderDTO> confirmOrder(Authentication authentication){

        User user = userService.getUserByEmail(authentication.getName());
        Order order = orderService.confirmOrder(shoppingCartService.getByUser(user));
        return new ResponseEntity<>(modelMapper.map(order, OrderDTO.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> ordersHistory(Authentication authentication){

        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(orderService.ordersHistory(user)
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList()));
    }
}
