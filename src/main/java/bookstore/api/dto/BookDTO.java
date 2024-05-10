package bookstore.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookDTO {

    @NotNull(message = "Name shouldn't be null")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 characters")
    private String name;

    @NotNull(message = "isbn shouldn't be null")
    @NotEmpty(message = "isbn shouldn't be empty")
    private String isbn;

    @NotNull(message = "Price shouldn't be null")
    private BigDecimal price;

    @NotNull(message = "IsAvailable shouldn't be null")
    private Boolean isAvailable;

    private List<BookCommentDTO> bookComments;
}
