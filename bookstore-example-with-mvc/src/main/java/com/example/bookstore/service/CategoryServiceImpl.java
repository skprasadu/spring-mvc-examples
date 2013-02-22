package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.domain.Category;
import com.example.bookstore.repository.CategoryRepository;

/**
 * @see CategoryService
 * 
 * 
 *
 */
@Service("categoryService")
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category findById(long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	@Override
	public void addCategory(Category category) {
		categoryRepository.storeCategory(category);
	}
}
