package com.raptor.ecommerceproject.models;

import java.util.Date;

public class Order {
    private Long id;
    private String numberOrder;
    private Date dateCreation;
    private Date dateReceived;
    private double total;

    public Order() {
    }

    public Order(Long id, String numberOrder, Date dateCreation, Date dateReceived, double total) {
        this.id = id;
        this.numberOrder = numberOrder;
        this.dateCreation = dateCreation;
        this.dateReceived = dateReceived;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(String numberOrder) {
        this.numberOrder = numberOrder;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", numberOrder='" + numberOrder + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateReceived=" + dateReceived +
                ", total=" + total +
                '}';
    }
}
