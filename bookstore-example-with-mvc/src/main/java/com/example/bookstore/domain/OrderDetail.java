package com.example.bookstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * An order detail is the link table between {@link Order} and {@link Book} We also store how many books are ordered in
 * the {@link #quantity} field
 * 
 * 
 * 
 * 
 */
@Entity
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    private int quantity = 1;

    public OrderDetail() {
        super();
    }

    public OrderDetail(Book book, int quantity) {
        super();
        this.book = book;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BigDecimal getPrice() {
        if (this.book != null) {
            return this.book.getPrice().multiply(new BigDecimal(this.quantity));
        }
        return BigDecimal.ZERO;
    }

}