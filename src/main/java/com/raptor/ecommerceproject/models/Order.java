package com.raptor.ecommerceproject.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = ("orders"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numberOrder;
    private Date dateCreation;
    private Date dateReceived;
    private double total;

    //Atributo donde se crea el usuario
    @ManyToOne
    private User userOrder;

    //Atributo para el detalle de la orden
    @OneToOne(mappedBy = "order")
    private OrderDetail orderDetail;

    public Order() {
    }

    public Order(Long id, String numberOrder, Date dateCreation, Date dateReceived, double total, User userOrder) {
        this.id = id;
        this.numberOrder = numberOrder;
        this.dateCreation = dateCreation;
        this.dateReceived = dateReceived;
        this.total = total;
        this.userOrder = userOrder;
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

    public User getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(User userOrder) {
        this.userOrder = userOrder;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
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
