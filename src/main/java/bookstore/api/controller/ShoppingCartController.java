package bookstore.api.controller;

import bookstore.api.dto.ShoppingCartDTO;
import bookstore.api.model.User;
import bookstore.api.service.BookService;
import bookstore.api.service.ShoppingCartService;
import bookstore.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final BookService bookService;

    @Autowired
    public ShoppingCartController(ModelMapper modelMapper, UserService userService,
                                  ShoppingCartService shoppingCartService, BookService bookService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.bookService = bookService;
    }

    @GetMapping("/by-user")
    public ResponseEntity<ShoppingCartDTO> getByUser(Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(modelMapper
                .map(shoppingCartService.getByUser(user), ShoppingCartDTO.class));
    }


    @PutMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(Authentication authentication, @RequestParam Long bookId){
        User user = userService.getUserByEmail(authentication.getName());
        shoppingCartService.addBook(bookService.getById(bookId), user);
    }

    @DeleteMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromCart(Authentication authentication, @RequestParam Long bookId){
        User user = userService.getUserByEmail(authentication.getName());
        shoppingCartService.removeBook(bookService.getById(bookId), user);
    }

    @DeleteMapping("/books/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearCart(Authentication authentication){
        User user = userService.getUserByEmail(authentication.getName());
        shoppingCartService.clearShoppingCart(shoppingCartService.getByUser(user));
    }
}
