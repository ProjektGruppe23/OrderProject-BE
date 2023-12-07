package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendorId;

    private String vendorName;

    @OneToMany(mappedBy = "vendor")
    @JsonBackReference
    private List<Order> orders;


    @OneToMany(mappedBy = "vendor")
    @JsonBackReference
    private List<ArchivedOrder> archivedOrders;

    public int getVendorId()
    {
        return vendorId;
    }

    public void setVendorId(int vendorId)
    {
        this.vendorId = vendorId;
    }

    public String getVendorName()
    {
        return vendorName;
    }

    public void setVendorName(String vendorName)
    {
        this.vendorName = vendorName;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public List<ArchivedOrder> getArchivedOrders()
    {
        return archivedOrders;
    }

    public void setArchivedOrders(List<ArchivedOrder> archivedOrders)
    {
        this.archivedOrders = archivedOrders;
    }
}

