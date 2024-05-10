package bookstore.api.service.impl;

import bookstore.api.model.Book;
import bookstore.api.model.ShoppingCart;
import bookstore.api.model.User;
import bookstore.api.repository.ShoppingCartRepository;
import bookstore.api.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void registerNewShoppingCart(User user){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional(readOnly = true)
    public ShoppingCart getByUser(User user) {
        return shoppingCartRepository.findByUser(user);
    }

    @Override
    public void addBook(Book book, User user){
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        shoppingCart.getBooks().add(book);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void removeBook(Book book, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        shoppingCart.getBooks().remove(book);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clearShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.getBooks().clear();
        shoppingCartRepository.save(shoppingCart);
    }
}
