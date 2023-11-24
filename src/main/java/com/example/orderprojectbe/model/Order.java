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
    private String title;
    private double price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "costumerAddress", referencedColumnName = "costumerAddressId")
    @JsonBackReference
    private CostumerAddress costumerAddress;

    @ManyToOne
    @JoinColumn(name = "vendor", referencedColumnName = "vendorId")
    private Vendor vendor;
}
