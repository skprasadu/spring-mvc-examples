package com.example.bookstore.service;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookSearchCriteria;
import com.example.bookstore.domain.Cart;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderDetail;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.repository.OrderRepository;

/**
 * @see BookstoreService
 * 
 * 
 *
 */
@Service("bookstoreService")
@Transactional(readOnly = true)
public class BookstoreServiceImpl implements BookstoreService {

    private static final int RANDOM_BOOKS = 2;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Book> findBooksByCategory(Category category) {
        return this.bookRepository.findByCategory(category);
    }

    @Override
    public List<Book> findRandomBooks() {
        return this.bookRepository.findRandom(RANDOM_BOOKS);
    }

    @Override
    @Transactional(readOnly = false)
    public Order store(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public List<Book> findBooks(BookSearchCriteria bookSearchCriteria) {
        return this.bookRepository.findBooks(bookSearchCriteria);
    }

    @Override
    public Book findBook(long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public List<Order> findOrdersForAccount(Account account) {
        return this.orderRepository.findByAccount(account);
    }

    @Override
    @Transactional(readOnly = false)
    public Order createOrder(Cart cart, Account customer) {
        Order order = new Order(customer);
        for (Entry<Book, Integer> line : cart.getBooks().entrySet()) {
            order.addOrderDetail(new OrderDetail(line.getKey(), line.getValue()));
        }
        order.setOrderDate(new Date());
        return order;
    }

    @Override
    public Order findOrder(long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void addBook(Book book) {
        this.bookRepository.storeBook(book);

    }
}
