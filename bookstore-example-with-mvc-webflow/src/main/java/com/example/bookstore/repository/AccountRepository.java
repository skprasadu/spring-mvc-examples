package com.example.bookstore.repository;

import com.example.bookstore.domain.Account;

/**
 * Repository for working with {@link Account} domain objects
 * 
 * 
 * 
 *
 */
public interface AccountRepository {

    Account findByUsername(String username);

    Account findById(long id);

    Account save(Account account);

}
