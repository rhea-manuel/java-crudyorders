package com.rheact.orders.controllers;

import com.rheact.orders.models.Customer;
import com.rheact.orders.services.CustomerServices;
import com.rheact.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @DeleteMapping(value = "/customer/{id}", produces = "application/json")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id){
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@PathVariable long id, @RequestBody Customer updatedInfo){
        customerService.update(updatedInfo, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> replaceCustomer(@PathVariable long id, @RequestBody @Valid Customer updatedCustomer){
        updatedCustomer.setCustcode(id);
        Customer c = customerService.save(updatedCustomer);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer toAdd){
//        System.out.println(toAdd);
        toAdd.setCustcode(0);
        toAdd = customerService.save(toAdd);

        HttpHeaders responseHeaders =  new HttpHeaders();
        URI toAddURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toAdd.getCustcode())
                .toUri();

        responseHeaders.setLocation(toAddURI);

        return new ResponseEntity<>(toAdd, responseHeaders, HttpStatus.CREATED);
    }

}
