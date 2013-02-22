package com.example.bookstore.repository;

import java.util.List;

import com.example.bookstore.domain.Category;

/**
 * Repository for working with {@link Category} domain objects
 *  
 * 
 * 
 *
 */
public interface CategoryRepository {

	List<Category> findAll();

	Category findById(long id);

	void storeCategory(Category category);
}
