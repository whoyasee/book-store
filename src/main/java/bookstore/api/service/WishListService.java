package bookstore.api.service;

import bookstore.api.model.Book;
import bookstore.api.model.User;
import bookstore.api.model.WishList;

public interface WishListService {

    void registerNewWishList(User user);

    WishList getByUser(User user);

    void clearWishList(WishList wishList);

    void addBook(Book book, User user);

    void removeBook(Book book, User user);
}
