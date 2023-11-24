package com.example.orderprojectbe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendorId;

    private String vendorName;

    @OneToMany(mappedBy = "vendor")
    private List<Order> orders;


}

