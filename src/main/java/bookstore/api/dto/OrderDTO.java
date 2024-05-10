package bookstore.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private BigDecimal totalPrice;
    private LocalDateTime created;
    private Set<BookDTO> books;
}
