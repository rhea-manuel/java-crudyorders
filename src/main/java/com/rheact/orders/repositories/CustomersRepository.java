package com.rheact.orders.repositories;

import com.rheact.orders.models.Customer;
import com.rheact.orders.views.OrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>  {
    List<Customer> findByCustnameContainingIgnoringCase(String name);

    @Query(value = "SELECT custname name, count(o.ordnum) countorders" +
            " FROM customers c LEFT JOIN orders o" +
            " ON c.custcode = o.custcode" +
            " GROUP by c.custname", nativeQuery = true)
    List<OrderCounts> orderCounts();
}
