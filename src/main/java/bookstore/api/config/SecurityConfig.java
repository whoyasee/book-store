package bookstore.api.config;

import bookstore.api.model.Role;
import bookstore.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/register").permitAll()

                                .requestMatchers(HttpMethod.GET, "/users/by-email", "/users").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/books", "/authors").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/authors/{authorId}/books/{bookId}").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/books/{id}", "/authors/{authorId}").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/books/{id}/comments/{commentId}").hasAuthority(Role.ADMIN.name())

                                .requestMatchers(HttpMethod.GET, "/authors", "/authors/{authorId}").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/books", "/books/{id}", "/books/{id}/comments").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/books/{id}/comments").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())

                                .requestMatchers(HttpMethod.GET, "/shopping-carts/by-user", "/wish-lists/by-user", "/orders").hasAuthority(Role.USER.name())
                                .requestMatchers(HttpMethod.POST,  "/orders/confirm").hasAuthority(Role.USER.name())
                                .requestMatchers(HttpMethod.PUT, "/shopping-carts/books", "/wish-lists/books").hasAuthority(Role.USER.name())
                                .requestMatchers(HttpMethod.DELETE, "/shopping-carts/books", "/shopping-carts/books/clear",
                                        "/wish-lists/books", "/wish-lists/books/clear").hasAuthority(Role.USER.name())

                                .anyRequest().authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

//    @PostConstruct
//    public void inject() {
//        User admin = User.builder()
//                .username("admin")
//                .password("admin")
//                .email("admin@gmail.com")
//                .role(Role.ADMIN)
//                .build();
//        userService.saveAdmin(admin);
//
//
//        UserDTO user = UserDTO.builder()
//                .username("user")
//                .password("user")
//                .matchPassword("user")
//                .email("user@gmail.com")
//                .build();
//        userService.save(user);
//    }
}
