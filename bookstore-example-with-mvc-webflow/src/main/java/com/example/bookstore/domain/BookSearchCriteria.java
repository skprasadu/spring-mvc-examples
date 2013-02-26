package com.example.bookstore.domain;

/**
 * Object to hold the search criteria to search books.
 * 
 * 
 * 
 *
 */
public class BookSearchCriteria {

    private String title;
    private Category category;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

}
