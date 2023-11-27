package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "`order`")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
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
    private CostumerAddress costumerAddress;

    @ManyToOne
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;
}
