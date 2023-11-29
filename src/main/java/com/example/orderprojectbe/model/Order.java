package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "`order`")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
@ToString(exclude = {"costumerAddress", "vendor"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String orderApiId;
    private String productName;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "costumerAddressId", referencedColumnName = "costumerAddressId")
    private CostumerAddress costumerAddress;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;

    public Order() {
    }

    public Order(String orderApiId, Vendor vendor) {
        this.orderApiId = orderApiId;
        this.vendor = vendor;
    }
}

