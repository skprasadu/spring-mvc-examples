package com.example.bookstore.repository;

import java.util.List;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookSearchCriteria;
import com.example.bookstore.domain.Category;

/**
 * Repository for working with {@link Book} domain objects
 * 
 * 
 * 
 *
 */
public interface BookRepository {

	Book findById(long id);

	List<Book> findByCategory(Category category);

	List<Book> findRandom(int count);

	List<Book> findBooks(BookSearchCriteria bookSearchCriteria);

	void storeBook(Book book);

}
