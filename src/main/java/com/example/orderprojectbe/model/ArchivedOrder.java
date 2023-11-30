package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "`ArchivedOrder`")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
@ToString(exclude = {"costumerAddress", "vendor"})
public class ArchivedOrder
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String orderApiId;
    private String productName;
    private double price;
    private int quantity;
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "costumerAddressId", referencedColumnName = "costumerAddressId")
    private CostumerAddress costumerAddress;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;
}
