package bookstore.api.service;

import bookstore.api.dto.UserDTO;
import bookstore.api.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User saveAdmin(User user);

    boolean save(UserDTO userDTO);

    User getUserByEmail(String username);
}
