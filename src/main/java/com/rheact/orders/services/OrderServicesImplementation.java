package com.rheact.orders.services;

//import com.rheact.orders.models.Agent;
import com.rheact.orders.models.Order;
import com.rheact.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderService")
public class OrderServicesImplementation implements OrderServices{

    @Autowired
    OrdersRepository ordersrepo;

    @Override
    public Order findbyId(long id) {
        Order toReturn = ordersrepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order at "+id+" not found."));
        return toReturn;
    }
}
