package com.example.orderprojectbe.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CostumerAddress
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int costumerAddressId;
    private String locality;
    private String street_address;
    private String extended_address;
    private String postal_code;

    private String name;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "countryId")
    private Country country;

    @OneToMany(mappedBy = "costumerAddress", cascade = CascadeType.REMOVE)
    private List<Order> order;
}
