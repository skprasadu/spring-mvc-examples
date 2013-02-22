package com.example.bookstore.repository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.config.InfrastructureContextConfiguration;
import com.example.bookstore.config.TestDataContextConfiguration;
import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookSearchCriteria;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.support.BookBuilder;
import com.example.bookstore.domain.support.CategoryBuilder;
import com.example.bookstore.domain.support.EntityBuilder.EntityBuilderManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class, TestDataContextConfiguration.class })
@Transactional
public class JpaBookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	@PersistenceContext
	private EntityManager entityManager;

	private Book book;
	private Category category;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		category = new CategoryBuilder() {
			{
				name("Evolution");
			}
		}.build();

		book = new BookBuilder() {
			{
				description("Richard Dawkins' brilliant reformulation of the theory of natural selection");
				author("Richard Dawkins");
				title("The Selfish Gene: 30th Anniversary Edition");
				isbn("9780199291151");
				category(category);
			}
		}.build();
	}

	@After
	public void tearDown() {
		EntityBuilderManager.clearEntityManager();
	}

	@Test
	public void testFindById() {
		entityManager.flush();
		Book book = bookRepository.findById(this.book.getId());
		assertEquals(this.book.getAuthor(), book.getAuthor());
		assertEquals(this.book.getDescription(), book.getDescription());
		assertEquals(this.book.getIsbn(), book.getIsbn());
	}

	@Test
	public void testFindByCategory() {
		setupData();
		List<Book> books = bookRepository.findByCategory(category);
		assertEquals(1, books.size());

		for (Book book : books) {
			assertEquals(this.book.getCategory().getId(), category.getId());
			assertEquals(this.book.getAuthor(), book.getAuthor());
			assertEquals(this.book.getDescription(), book.getDescription());
			assertEquals(this.book.getIsbn(), book.getIsbn());
		}
	}

	@Test
	@Rollback(true)
	public void testStoreBook() {
		Book book = new BookBuilder() {
			{
				description("Something");
				author("JohnDoe");
				title("John Doe's life");
				isbn("1234567890123");
				category(category);
			}
		}.build();

		bookRepository.storeBook(book);

		// Explicitly flush so any CUD query that is left behind is send to the database before rolling back
		entityManager.flush();
		
		Book book1 = bookRepository.findById(book.getId());
		
		assertEquals(book1.getAuthor(), book.getAuthor());
		assertEquals(book1.getDescription(), book.getDescription());
		assertEquals(book1.getIsbn(), book.getIsbn());
	}
	
	@Test
    public void testFindBooks() {
		BookSearchCriteria bookSearchCriteria = new BookSearchCriteria();
		bookSearchCriteria.setTitle(book.getTitle());
		List<Book> books = bookRepository.findBooks(bookSearchCriteria);
		
		for (Book book : books) {
			assertEquals(this.book.getCategory().getId(), category.getId());
			assertEquals(this.book.getAuthor(), book.getAuthor());
			assertEquals(this.book.getDescription(), book.getDescription());
			assertEquals(this.book.getIsbn(), book.getIsbn());
		}
    }

	@Test
    public void testFindRandom() {
		List<Book> books = bookRepository.findRandom(0);
		assertTrue(books.size() != 0);
    }
}