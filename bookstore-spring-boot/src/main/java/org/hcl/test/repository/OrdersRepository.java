package org.hcl.test.repository;

import org.hcl.test.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("ordersRepository")
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	@Query("SELECT o FROM Orders o WHERE o.accountId=:uId and o.isInCart=true")
	Orders getCart(@Param("uId") int userId);
	
	@Modifying  
	@Transactional
	@Query("update Orders o set o.isInCart=false where o.accountId=:uId")
	void checkoutCart(@Param("uId") int userId);
}
