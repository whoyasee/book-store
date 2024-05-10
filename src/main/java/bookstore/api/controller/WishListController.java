package bookstore.api.controller;

import bookstore.api.dto.WishListDTO;
import bookstore.api.model.User;
import bookstore.api.service.BookService;
import bookstore.api.service.UserService;
import bookstore.api.service.WishListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wish-lists")
public class WishListController {

    private final ModelMapper modelMapper;
    private final WishListService wishListService;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public WishListController(ModelMapper modelMapper, WishListService wishListService, BookService bookService, UserService userService) {
        this.modelMapper = modelMapper;
        this.wishListService = wishListService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/by-user")
    public ResponseEntity<WishListDTO> getByUser(Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(modelMapper
                .map(wishListService.getByUser(user), WishListDTO.class));
    }



    @PutMapping("/books")
    public void addToList(Authentication authentication, @RequestParam Long bookId){
        User user = userService.getUserByEmail(authentication.getName());
        wishListService.addBook(bookService.getById(bookId), user);
    }

    @DeleteMapping("/books")
    public void removeFromList(Authentication authentication, @RequestParam Long bookId){
        User user = userService.getUserByEmail(authentication.getName());
        wishListService.removeBook(bookService.getById(bookId), user);
    }

    @DeleteMapping("/books/clear")
    public void clearList(Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        wishListService.clearWishList(wishListService.getByUser(user));
    }
}
