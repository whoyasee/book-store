package bookstore.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookCommentDTO {

    @NotNull(message = "Text shouldn't be null")
    @NotEmpty(message = "Text shouldn't be empty")
    private String text;

    private LocalDateTime createdAt;
}
