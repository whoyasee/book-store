package bookstore.api.service;

import bookstore.api.model.Book;
import bookstore.api.model.ShoppingCart;
import bookstore.api.model.User;

public interface ShoppingCartService {
    void registerNewShoppingCart(User user);

    ShoppingCart getByUser(User user);

    void clearShoppingCart(ShoppingCart shoppingCart);

    void addBook(Book book, User user);

    void removeBook(Book book, User user);

}
