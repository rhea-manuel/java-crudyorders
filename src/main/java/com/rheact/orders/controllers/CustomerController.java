package com.rheact.orders.controllers;

import com.rheact.orders.models.Customer;
import com.rheact.orders.services.CustomerServices;
import com.rheact.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServices customerService;

    @GetMapping(value="/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers(){
        List<Customer> customers = customerService.allCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{id}", produces = "application/json")
    public ResponseEntity<?> findCustomerbyId(@PathVariable long id) {
        Customer customer = customerService.findbyId(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping(value = "/namelike/{name}", produces = "application/json")
    public ResponseEntity<?> findCustomerbyId(@PathVariable String name) {
        List<Customer> customers = customerService.findByName(name);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> countOrders(){
        List<OrderCounts> counts = customerService.orderCounts();
        return new ResponseEntity<>(counts, HttpStatus.OK);
    }
}
