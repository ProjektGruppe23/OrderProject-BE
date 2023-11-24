package com.example.orderprojectbe.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CostumerAddress
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int costumerAddressId;
    private String city;
    private String streetAddress;
    private String extendedAddress;
    private String postalCode;

    private String costumerName;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "countryId")
    private Country country;

    @OneToMany(mappedBy = "costumerAddress", cascade = CascadeType.REMOVE)
    private List<Order> order;
}
