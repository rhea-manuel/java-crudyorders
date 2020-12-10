package com.rheact.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,
            unique = true
    )
    private long paymentid;

    @Column (nullable = false)
    private String Type;

    @ManyToMany(mappedBy = "payments")
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    public Payment() {
    }

    public Payment(String type) {
        Type = type;
    }

    public long getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(long paymentid) {
        this.paymentid = paymentid;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


}
