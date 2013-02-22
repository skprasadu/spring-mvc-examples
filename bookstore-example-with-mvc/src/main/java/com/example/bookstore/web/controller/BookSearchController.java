package com.example.bookstore.web.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookSearchCriteria;
import com.example.bookstore.domain.Category;
import com.example.bookstore.service.BookstoreService;

/**
 * Controller to handle book search requests.
 * 
 * 
 * 
 *
 */
@Controller
public class BookSearchController {

    @Autowired
    private BookstoreService bookstoreService;

    @ModelAttribute
    public BookSearchCriteria criteria() {
        return new BookSearchCriteria();
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return this.bookstoreService.findAllCategories();
    }

    /**
     * This method searches our database for books based on the given {@link BookSearchCriteria}. 
     * Only books matching the criteria are returned.
     * 
     * @param criteria the criteria used for searching
     * @return the found books
     * 
     * @see com.example.bookstore.repository.BookRepository#findBooks(BookSearchCriteria)
     */
    @RequestMapping(value = "/book/search", method = { RequestMethod.GET })
    public Collection<Book> list(@ModelAttribute("bookSearchCriteria") BookSearchCriteria criteria) {
        return this.bookstoreService.findBooks(criteria);
    }

}
