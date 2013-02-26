package com.example.bookstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * 
 * 
 *
 */
@Entity
// order is a reserved SQL keyword, hence the explicit table definition
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "houseNumber", column = @Column(name = "shipping_houseNumber")),
            @AttributeOverride(name = "boxNumber", column = @Column(name = "shipping_boxNumber")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_postalCode")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_country")) })
    private Address shippingAddress;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "houseNumber", column = @Column(name = "billing_houseNumber")),
            @AttributeOverride(name = "boxNumber", column = @Column(name = "billing_boxNumber")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "billing_postalCode")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")) })
    private Address billingAddress;

    @ManyToOne(optional = false)
    private Account account;

    private boolean billingSameAsShipping = true;

    private Date orderDate;
    private Date deliveryDate;

    private BigDecimal totalOrderPrice = null;

    // One to many creates a join table by default, prevent this as
    // order detail is our specific join table
    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

    public Order() {
        super();
    }

    public Order(Account account) {
        super();
        this.account = account;
        this.shippingAddress = new Address(account.getAddress());
    }

    public Address getShippingAddress() {
        return this.shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        if (this.billingSameAsShipping) {
            return this.shippingAddress;
        }
        return this.billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public boolean isBillingSameAsShipping() {
        return this.billingSameAsShipping;
    }

    public void setBillingSameAsShipping(boolean billingSameAsShipping) {
        this.billingSameAsShipping = billingSameAsShipping;
    }

    public Long getId() {
        return this.id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public List<OrderDetail> getOrderDetails() {
        return this.orderDetails;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getTotalOrderPrice() {
        return this.totalOrderPrice;
    }

    public int getTotalNumberOfbooks() {
        int total = 0;
        for (OrderDetail orderDetail : getOrderDetails()) {
            total += orderDetail.getQuantity();
        }
        return total;
    }

    /**
     * Update the order details and update the total price. If the quantity is 0 or less the order detail is removed from the list.
     */
    public void updateOrderDetails() {
        BigDecimal total = BigDecimal.ZERO;
        Iterator<OrderDetail> details = this.orderDetails.iterator();
        while (details.hasNext()) {
            OrderDetail detail = details.next();
            if (detail.getQuantity() <= 0) {
                details.remove();
            } else {
                total = total.add(detail.getPrice());

            }
        }
        total.setScale(2, RoundingMode.HALF_UP);
        this.totalOrderPrice = total;
    }

    public void addOrderDetail(OrderDetail detail) {
        if (this.orderDetails.add(detail)) {
            if (this.totalOrderPrice == null) {
                this.totalOrderPrice = detail.getPrice();
            } else {
                this.totalOrderPrice = this.totalOrderPrice.add(detail.getPrice());
            }
        }
    }
}
