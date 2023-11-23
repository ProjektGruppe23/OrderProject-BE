package com.example.orderprojectbe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CostumerAddress
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String locality;
    private String country;
    private String street_address;
    private String extended_address;
    private String postal_code;

    private String name;
    private String phone;
}
