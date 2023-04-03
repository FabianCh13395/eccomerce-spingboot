package com.raptor.ecommerceproject.services;


import com.raptor.ecommerceproject.models.Order;
import com.raptor.ecommerceproject.models.User;

import java.util.List;

public interface IOrderService {
    List<Order> findAll();
    Order save(Order order);
    String generateNumberOrder();
    List<Order> findByUserOrder(User user);
}
