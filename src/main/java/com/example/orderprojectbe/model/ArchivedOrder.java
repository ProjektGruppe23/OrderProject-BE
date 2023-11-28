package com.example.orderprojectbe.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ArchivedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int archivedOrderId;

    private String productName;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "countryId", referencedColumnName = "countryId")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;
}
