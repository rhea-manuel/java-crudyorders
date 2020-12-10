package com.rheact.orders.controllers;

import com.rheact.orders.models.Order;
import com.rheact.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServices orderService;

    @GetMapping( value = "/order/{id}", produces = "application/json")
    public ResponseEntity<?> getOrderbyId(@PathVariable long id){
        Order toReturn = orderService.findbyId(id);
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }
}
