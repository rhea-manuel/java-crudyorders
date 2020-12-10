package com.rheact.orders.services;

import com.rheact.orders.models.Agent;
import com.rheact.orders.models.Customer;
import com.rheact.orders.models.Order;
import com.rheact.orders.repositories.AgentsRepository;
import com.rheact.orders.repositories.CustomersRepository;
import com.rheact.orders.repositories.OrdersRepository;
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

    @Autowired
    private OrdersRepository orderrepos;

    @Autowired
    private AgentsRepository agentrepos;

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

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0) {
            customerrepos.findById(customer.getCustcode())
                    .orElseThrow(()->new EntityNotFoundException("Customer"+ customer.getCustcode()+" is not found."));
            newCustomer.setCustcode(customer.getCustcode());
        }

//        Strings
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setPhone(customer.getPhone());

//        Doubles
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());

        Agent a = customer.getAgent();

        Agent newAgent = agentrepos.findById(a.getAgentcode())
                .orElseThrow(()-> new EntityNotFoundException("Agent" + a.getAgentcode()+ " not found"));

        newCustomer.setAgent(newAgent);
//        newCustomer.getAgent().clear();
//        for (Agent a : customer.getAgent()){
//            Order newOrder = agentrepos.findById(a.getAgentcode()))
//                    .orElseThrow(()-> new EntityNotFoundException("Agent"+ a.getAgentcode()+"not found!"));
//            newCustomer.getOrders().add(newOrder);
//        }

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()) {
            Order newOrder = new Order();
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setCustomer(newCustomer);
            newCustomer.getOrders().add(newOrder);
        }

        return customerrepos.save(newCustomer);
    }



    @Transactional
    @Override
    public void delete(long id) {
        customerrepos.deleteById(id);
    }

    @Transactional
    @Override
    public Customer update(Customer toUpdate, long id) {
        Customer updatedCustomer = customerrepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Customer id "+id+" not found!"));

        //        Strings
        if (toUpdate.getCustname() !=null) updatedCustomer.setCustname(toUpdate.getCustname());
        if (toUpdate.getCustcity() !=null) updatedCustomer.setCustcity(toUpdate.getCustcity());
        if (toUpdate.getWorkingarea() !=null)updatedCustomer.setWorkingarea(toUpdate.getWorkingarea());
        if (toUpdate.getCustcountry() !=null) updatedCustomer.setCustcountry(toUpdate.getCustcountry());
        if (toUpdate.getGrade() !=null) updatedCustomer.setGrade(toUpdate.getGrade());
        if (toUpdate.getPhone() !=null) updatedCustomer.setPhone(toUpdate.getPhone());

//        Doubles
        if (toUpdate.hasOutstandingAmt) updatedCustomer.setOutstandingamt(toUpdate.getOutstandingamt());
        if (toUpdate.hasPaymentAmt) updatedCustomer.setPaymentamt(toUpdate.getPaymentamt());
        if(toUpdate.hasReceiveAmt) updatedCustomer.setReceiveamt(toUpdate.getReceiveamt());

        if (toUpdate.getOrders().size()>0){
            for (Order o : toUpdate.getOrders()) {
                Order newOrder = new Order();
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrderdescription(o.getOrderdescription());
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setCustomer(updatedCustomer);
                updatedCustomer.getOrders().add(newOrder);
            }
        }

        if (toUpdate.getAgent()!=null){
            Agent a = toUpdate.getAgent();

            Agent newAgent = agentrepos.findById(a.getAgentcode())
                    .orElseThrow(()-> new EntityNotFoundException("Agent" + a.getAgentcode()+ " not found"));

            updatedCustomer.setAgent(newAgent);
        }
        
        return customerrepos.save(updatedCustomer);
    }

    @Transactional
    @Override
    public void deleteAll() {
        customerrepos.deleteAll();
    }
}
