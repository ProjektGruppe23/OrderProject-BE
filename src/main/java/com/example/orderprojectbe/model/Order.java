package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "`order`")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int orderApiId;
    private String productName;
    private double price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "costumerAddressId", referencedColumnName = "costumerAddressId")
    @JsonBackReference
    private CostumerAddress costumerAddress;

    @ManyToOne
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;
}
