package bookstore.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean isAvailable;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    private List<BookComment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

    public void addComment(BookComment comment){
        comment.setBook(this);
        comments.add(comment);
    }

    public void removeComment(BookComment comment){
        comment.setBook(null);
        comments.remove(comment);
    }
}
