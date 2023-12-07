package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String productName;
    private double price;
    private int quantity;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "countryId", referencedColumnName = "countryId")
    private Country country;
    private String apiId;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;

    public ArchivedOrder()
    {
    }

    public ArchivedOrder(String productName, Country country, double price, int quantity, String apiId, Vendor vendor)
    {
        this.productName = productName;
        this.country = country;
        this.price = price;
        this.quantity = quantity;
        this.apiId = apiId;
        this.vendor = vendor;
    }

}
