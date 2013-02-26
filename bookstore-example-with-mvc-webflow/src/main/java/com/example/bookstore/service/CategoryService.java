package com.example.bookstore.service;

import java.util.List;

import com.example.bookstore.domain.Category;

/**
 * Contract for services that work with an {@link Category}.
 * 
 * 
 * 
 * 
 */
public interface CategoryService {

	Category findById(long id);

	List<Category> findAll();

	void addCategory(Category category);

}
