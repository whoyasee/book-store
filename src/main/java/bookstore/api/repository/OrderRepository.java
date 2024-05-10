package bookstore.api.repository;

import bookstore.api.model.Order;
import bookstore.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o " +
            "left join fetch o.books b " +
            "left join fetch b.comments " +
            "where o.user = :user")
    List<Order> ordersHistory(@Param("user") User user);
}
