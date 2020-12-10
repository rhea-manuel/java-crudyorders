package com.rheact.orders.services;

import com.rheact.orders.models.Customer;
import com.rheact.orders.repositories.CustomersRepository;
import com.rheact.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServicesImplementation implements CustomerServices {

    @Autowired
    private CustomersRepository customerrepos;

    @Override
    public List<Customer> allCustomers() {
        List<Customer> toReturn = new ArrayList<>();
        customerrepos.findAll().iterator().forEachRemaining(toReturn::add);

        return toReturn;
    }

    @Override
    public Customer findbyId(long id) {
        Customer toReturn = customerrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Customer at "+id+" not found."));
        return toReturn;
    }

    @Override
    public List<Customer> findByName(String name) {
        List<Customer> customers = customerrepos.findByCustnameContainingIgnoringCase(name);
        return customers;
    }

    @Override
    public List<OrderCounts> orderCounts() {
        return customerrepos.orderCounts();
    }
}
