package com.example.bookstore.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.bookstore.domain.Category;

/**
 * JPA based {@link CategoryRepository} implementation.
 *  
 * 
 * 
 *
 */
@Repository("categoryRepository")
public class JpaCategoryRepository implements CategoryRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Category> findAll() {
		String hql = "select c from Category c order by c.name";
		TypedQuery<Category> query = this.entityManager.createQuery(hql, Category.class);
		return query.setHint("org.hibernate.cacheable", true).getResultList();
	}

	@Override
	public Category findById(long id) {
		return this.entityManager.find(Category.class, id);
	}

	@Override
	public void storeCategory(Category category) {
		entityManager.persist(category);
	}
}
