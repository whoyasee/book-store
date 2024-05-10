package bookstore.api.repository;

import bookstore.api.model.ShoppingCart;
import bookstore.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("select sc from ShoppingCart sc " +
            "left join fetch sc.books b " +
            "left join fetch b.comments " +
            "where sc.user = :user")
    ShoppingCart findByUser(@Param("user") User user);

}
