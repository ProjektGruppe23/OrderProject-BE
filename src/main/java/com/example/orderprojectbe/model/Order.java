package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String orderApiId;
    private String productName;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "costumerAddressId", referencedColumnName = "costumerAddressId")
    private CostumerAddress costumerAddress;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "vendorId", referencedColumnName = "vendorId")
    private Vendor vendor;

    /*public Order() {
    }

    public Order(String orderApiId, Vendor vendor) {
        this.orderApiId = orderApiId;
        this.vendor = vendor;
    }*/

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderApiId()
    {
        return orderApiId;
    }

    public void setOrderApiId(String orderApiId)
    {
        this.orderApiId = orderApiId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public CostumerAddress getCostumerAddress()
    {
        return costumerAddress;
    }

    public void setCostumerAddress(CostumerAddress costumerAddress)
    {
        this.costumerAddress = costumerAddress;
    }

    public Vendor getVendor()
    {
        return vendor;
    }

    public void setVendor(Vendor vendor)
    {
        this.vendor = vendor;
    }
}

