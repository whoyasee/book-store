package bookstore.api.repository;

import bookstore.api.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTests {


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void AuthorRepository_Save_ReturnSavedAuthor(){
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .build();

        Author savedAuthor = authorRepository.save(author);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isGreaterThan(0);
    }

    @Test
    public void AuthorRepository_FindById_ReturnAuthor(){
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .build();

        authorRepository.save(author);

        Author authorById = authorRepository.findById(author.getId()).get();

        assertThat(authorById).isNotNull();
    }

    @Test
    public void AuthorRepository_DeleteAuthor_ReturnAuthorIsEmpty(){
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .build();

        authorRepository.save(author);


        authorRepository.delete(author);
        Optional<Author> authorById = authorRepository.findById(author.getId());


        assertThat(authorById).isEmpty();
    }


    @Test
    public void AuthorRepository_FindByIdFetchBooks_ReturnAuthor(){
        Book book =  Book.builder()
                .name("book")
                .isbn("isbn")
                .price(BigDecimal.valueOf(100))
                .isAvailable(true)
                .comments(new ArrayList<>())
                .build();
        this.entityManager.persistAndFlush(book);
        Author author = Author.builder()
                .firstName("first name")
                .lastName("last name")
                .books(Set.of(book))
                .build();
        this.entityManager.persistAndFlush(author);
        this.entityManager.clear();


        Author authorResult = authorRepository.findByIdFetchBooks(author.getId()).get();


        assertThat(authorResult.getBooks().size()).isEqualTo(1);
    }

    @Test
    public void AuthorRepository_FindAllFetchBooks_ReturnAuthors(){


    }
}
