package bookstore.api.service.impl;

import bookstore.api.dto.UserDTO;
import bookstore.api.exception_handling.NotFoundException;
import bookstore.api.exception_handling.WrongPasswordException;
import bookstore.api.model.Role;
import bookstore.api.model.User;
import bookstore.api.repository.UserRepository;
import bookstore.api.service.ShoppingCartService;
import bookstore.api.service.UserService;
import bookstore.api.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final WishListService wishListService;
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(WishListService wishListService, UserRepository userRepository,
                           ShoppingCartService shoppingCartService, PasswordEncoder passwordEncoder) {
        this.wishListService = wishListService;
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("User with email" + email + " not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(UserDTO userDTO){
        if (!(userDTO.getPassword().equals(userDTO.getMatchPassword())))
            throw new WrongPasswordException("Passwords do not match");

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        wishListService.registerNewWishList(user);
        shoppingCartService.registerNewShoppingCart(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));


        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                roles
        );
    }

    @Override
    public User saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
