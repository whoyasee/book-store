package bookstore.api.dto;

import bookstore.api.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotNull(message = "Username shouldn't be null")
    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 2, max = 255, message = "Username should be between 2 and 255 characters")
    private String username;

    @NotNull(message = "Password shouldn't be null")
    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min = 5, max = 20, message = "Password should be between 5 and 20 characters")
    private String password;

    @NotNull(message = "MatchPassword shouldn't be null")
    @NotEmpty(message = "MatchPassword shouldn't be empty")
    @Size(min = 5, max = 20, message = "MatchPassword should be between 5 and 20 characters")
    private String matchPassword;

    @NotNull(message = "Email shouldn't be null")
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Invalid email address")
    @UniqueEmail(message = "Email should be unique")
    private String email;
}
