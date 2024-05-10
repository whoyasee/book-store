package bookstore.api.service;

import bookstore.api.model.Order;
import bookstore.api.model.ShoppingCart;
import bookstore.api.model.User;

import java.util.List;

public interface OrderService {

    Order confirmOrder(ShoppingCart shoppingCart);

    List<Order> ordersHistory(User user);
}
