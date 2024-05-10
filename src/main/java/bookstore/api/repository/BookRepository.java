package bookstore.api.repository;

import bookstore.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b " +
            "left join fetch b.comments")
    List<Book> findAllFetchComments();

    @Query("select b from Book b " +
            "left join fetch b.comments " +
            "where b.id = :id")
    Optional<Book> findByIdFetchComments(@Param("id") Long id);
}
