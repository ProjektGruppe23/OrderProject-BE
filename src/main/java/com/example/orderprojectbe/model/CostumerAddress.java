package com.example.orderprojectbe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
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
    @JoinColumn(name = "countryId", referencedColumnName = "countryId")
    private Country country;

    @OneToMany(mappedBy = "costumerAddress", cascade = CascadeType.REMOVE)
    private List<Order> order;
}
