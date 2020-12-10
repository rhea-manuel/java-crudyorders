package com.rheact.orders.controllers;

import com.rheact.orders.models.Customer;
import com.rheact.orders.models.Order;
import com.rheact.orders.services.OrderServices;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

    @PostMapping(value = "/order", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        order.setOrdnum(0);
        order = orderService.save(order);

        HttpHeaders responseHeaders =  new HttpHeaders();
        URI toAddURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getOrdnum())
                .toUri();

        responseHeaders.setLocation(toAddURI);

        return new ResponseEntity<>(order, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{id}", consumes = "application/json")
    public ResponseEntity<?> replaceOrder(@PathVariable long id, @RequestBody @Valid Order order){
        order.setOrdnum(id);
        Order o = orderService.save(order);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<?> deleteOrder(@PathVariable long id){
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
