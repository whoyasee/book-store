package bookstore.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthorDTO {

    @NotNull(message = "firstName shouldn't be null")
    @NotEmpty(message = "firstName shouldn't be empty")
    @Size(min = 2, max = 255, message = "firstName should be between 2 and 255 characters")
    private String firstName;

    @NotNull(message = "lastName shouldn't be null")
    @NotEmpty(message = "lastName shouldn't be empty")
    @Size(min = 2, max = 255, message = "lastName should be between 2 and 255 characters")
    private String lastName;

    private Set<BookDTO> books;
}
