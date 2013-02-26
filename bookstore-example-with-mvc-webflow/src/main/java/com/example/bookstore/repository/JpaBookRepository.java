package com.example.bookstore.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookSearchCriteria;
import com.example.bookstore.domain.Category;

/**
 * JPA implementation for the {@link BookRepository}.
 * 
 * 
 * 
 * 
 */
@Repository("bookRepository")
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book findById(long id) {
        return this.entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> findByCategory(Category category) {
        String hql = "select b from Book b where b.category=:category";
        TypedQuery<Book> query = this.entityManager.createQuery(hql, Book.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<Book> findRandom(int count) {
        String hql = "select b from Book b order by rand()";
        TypedQuery<Book> query = this.entityManager.createQuery(hql, Book.class);
        query.setMaxResults(count);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooks(BookSearchCriteria bookSearchCriteria) {
        Assert.notNull(bookSearchCriteria, "Search Criteria are required!");
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = builder.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (StringUtils.hasText(bookSearchCriteria.getTitle())) {
            String title = bookSearchCriteria.getTitle().toUpperCase();
            predicates.add(builder.like(builder.upper(book.<String> get("title")), "%" + title + "%"));
        }

        if (bookSearchCriteria.getCategory() != null) {
            Category category = this.entityManager.find(Category.class, bookSearchCriteria.getCategory().getId());
            predicates.add(builder.equal(book.<Category> get("category"), category));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        query.orderBy(builder.asc(book.get("title")));
        return this.entityManager.createQuery(query).getResultList();
    }

    @Override
    public void storeBook(Book book) {
        this.entityManager.merge(book);
    }
}
