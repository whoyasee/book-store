package bookstore.api.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WishListDTO {

    private Set<BookDTO> books;
}
