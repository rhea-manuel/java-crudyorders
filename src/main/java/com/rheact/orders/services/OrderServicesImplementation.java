package com.rheact.orders.services;

//import com.rheact.orders.models.Agent;
import com.rheact.orders.models.Customer;
import com.rheact.orders.models.Order;
import com.rheact.orders.models.Payment;
import com.rheact.orders.repositories.CustomersRepository;
import com.rheact.orders.repositories.OrdersRepository;
import com.rheact.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderService")
public class OrderServicesImplementation implements OrderServices{

    @Autowired
    OrdersRepository ordersrepo;

    @Autowired
    CustomersRepository customersrepo;

    @Autowired
    PaymentRepository paymentsrepo;

    @Override
    public Order findbyId(long id) {
        Order toReturn = ordersrepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Order at "+id+" not found."));
        return toReturn;
    }

    @Transactional
    @Override
    public Order save(Order order) {


        Order newOrder = new Order();

        if (order.getOrdnum()!=0){
            newOrder = ordersrepo.findById(order.getOrdnum())
                    .orElseThrow(()-> new EntityNotFoundException("Order id "+ order.getOrdnum()+" not found!"));
        }

//        Set to parameters
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setAdvanceamount(order.getAdvanceamount());

//        Get relationship parameters

//        First, the customer.
        Customer c = order.getCustomer();
        Customer newCustomer = customersrepo.findById(c.getCustcode())
                .orElseThrow(()-> new EntityNotFoundException("Customer at id "+c.getCustcode()+" was not found."));

        newOrder.setCustomer(newCustomer);
//        Next, the payments. First clear the current payments.
          newOrder.getPayments().clear();

//          Loop through the payments provided and add them one by one.
        for (Payment p : order.getPayments()){
            Payment newPayment = paymentsrepo.findById(p.getPaymentid())
                    .orElseThrow(()-> new EntityNotFoundException("Payment"+ p.getPaymentid()+" not found!"));
            newOrder.getPayments().add(newPayment);
        }

//        Return the final order.
        return ordersrepo.save(newOrder);
    }

    @Transactional
    @Override
    public void delete(long id) {
        ordersrepo.deleteById(id);
    }
}
