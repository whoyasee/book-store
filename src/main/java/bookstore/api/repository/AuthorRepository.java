package bookstore.api.repository;

import bookstore.api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a " +
            "left join fetch a.books b " +
            "left join fetch b.comments")
    List<Author> findAllFetchBooks();

    @Query("select a from Author a " +
            "left join fetch a.books b " +
            "left join fetch b.comments " +
            "where a.id = :id")
    Optional<Author> findByIdFetchBooks(@Param("id") Long id);
}
