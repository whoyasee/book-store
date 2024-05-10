package bookstore.api.service.impl;

import bookstore.api.model.Book;
import bookstore.api.model.User;
import bookstore.api.model.WishList;
import bookstore.api.repository.WishListRepository;
import bookstore.api.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    @Autowired
    public WishListServiceImpl(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @Override
    public void registerNewWishList(User user) {
        WishList wishList = new WishList();
        wishList.setUser(user);
        wishListRepository.save(wishList);
    }

    @Override
    @Transactional(readOnly = true)
    public WishList getByUser(User user) {
        return wishListRepository.findByUser(user);
    }

    @Override
    public void clearWishList(WishList wishList) {
        wishList.getBooks().clear();
        wishListRepository.save(wishList);
    }

    @Override
    public void addBook(Book book, User user) {
        WishList wishList = wishListRepository.findByUser(user);
        wishList.getBooks().add(book);
        wishListRepository.save(wishList);
    }

    @Override
    public void removeBook(Book book, User user) {
        WishList wishList = wishListRepository.findByUser(user);
        wishList.getBooks().remove(book);
        wishListRepository.save(wishList);
    }
}
