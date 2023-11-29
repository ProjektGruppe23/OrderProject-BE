package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @JoinColumn(name = "countryId", referencedColumnName = "countryId")
    private Country country;

    @OneToMany(mappedBy = "costumerAddress", cascade = CascadeType.REMOVE)
    private List<Order> order;

    public CostumerAddress(){};

    public CostumerAddress(String city, String streetAddress, String extendedAddress, String postalCode)
    {
        this.city = city;
        this.streetAddress = streetAddress;
        this.extendedAddress = extendedAddress;
        this.postalCode = postalCode;
    }

}
