package bookstore.api.repository;

import bookstore.api.model.User;
import bookstore.api.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("select wl from WishList wl " +
            "left join fetch wl.books b " +
            "left join fetch b.comments " +
            "where wl.user = :user")
    WishList findByUser(@Param("user") User user);
}
