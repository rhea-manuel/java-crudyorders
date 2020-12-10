package com.rheact.orders.services;

import com.rheact.orders.models.Order;

public interface OrderServices {
    Order findbyId (long id);
    Order save(Order order);
    void delete(long id);
//    long count();
}
