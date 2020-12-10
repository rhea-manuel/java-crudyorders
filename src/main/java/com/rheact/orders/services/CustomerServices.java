package com.rheact.orders.services;

import com.rheact.orders.models.Customer;
import com.rheact.orders.views.OrderCounts;

import java.util.List;

public interface CustomerServices {
    List<Customer> allCustomers();
    Customer findbyId(long id);
    List<Customer> findByName(String name);

    List<OrderCounts> orderCounts();
}
