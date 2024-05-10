package bookstore.api.service.impl;

import bookstore.api.model.Book;
import bookstore.api.model.Order;
import bookstore.api.model.ShoppingCart;
import bookstore.api.model.User;
import bookstore.api.repository.OrderRepository;
import bookstore.api.service.OrderService;
import bookstore.api.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public Order confirmOrder(ShoppingCart shoppingCart) {

        Order order = new Order();
        order.setCreated(LocalDateTime.now());
        order.setUser(shoppingCart.getUser());
        shoppingCart.getBooks()
                .forEach(book -> order.getBooks().add(book));
        order.setTotalPrice(order.getBooks()
                .stream()
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        orderRepository.save(order);
        shoppingCartService.clearShoppingCart(shoppingCart);
        return order;
    }

    @Override
    public List<Order> ordersHistory(User user) {
        return orderRepository.ordersHistory(user);
    }

}
