package org.hcl.test.repository;

import java.util.List;

import org.hcl.test.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("orderDetailsRepository")
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

	@Query("SELECT o FROM OrderDetails o WHERE o.ordersId=:oId")
    List<OrderDetails> getOrderDetails(@Param("oId") int oId);
	
	@Query("SELECT o FROM OrderDetails o WHERE o.ordersId=:oId and o.bookId=:bId")
    List<OrderDetails> getBookInCart(@Param("oId") int oId, @Param("bId") int bId);
}
