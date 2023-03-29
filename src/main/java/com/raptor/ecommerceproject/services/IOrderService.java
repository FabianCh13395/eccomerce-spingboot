package com.raptor.ecommerceproject.services;


import com.raptor.ecommerceproject.models.Order;

import java.util.List;

public interface IOrderService {
    List<Order> findAll();
    Order save(Order order);
}
