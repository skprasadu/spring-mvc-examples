package com.example.bookstore.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookstoreService;

/**
 * Controller to handle book detail requests.
 * 
 * 
 * 
 *
 */
@Controller
public class BookDetailController {

    @Autowired
    private BookstoreService bookstoreService;

    /**
     * Method used to prepare our model and select the view to show the details of the selected book.
     * @param bookId the id of the book
     * @param model the implicit model
     * @return view name to render (book/detail)
     */
    @RequestMapping(value = "/book/detail/{bookId}")
    public String details(@PathVariable("bookId") long bookId, Model model) {
        Book book = this.bookstoreService.findBook(bookId);
        model.addAttribute(book);
        return "book/detail";
    }
}
