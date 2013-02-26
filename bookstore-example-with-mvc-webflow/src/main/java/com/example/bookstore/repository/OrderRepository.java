package com.example.bookstore.repository;

import java.util.List;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.Order;

/**
 * Repository for working with {@link Order} domain objects
 *  
 * 
 * 
 *
 */
public interface OrderRepository {

    /**
     * Find the {@link Order} for the given id.
     * 
     * @param id id of the order to find.
     * @return the order belonging to the id.
     */
    Order findById(long id);

    /**
     * Save the order in the databse.
     */
    Order save(Order order);

    /**
     * Find the orders for the given {@link Account}.
     * @param customer the account
     * @return list of orders for the account, never <code>null</code>
     */
    List<Order> findByAccount(Account account);

}
