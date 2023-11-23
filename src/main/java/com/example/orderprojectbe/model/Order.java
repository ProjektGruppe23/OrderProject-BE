package com.example.orderprojectbe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "`order`")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_number;
    private String title;
    private double price;
    private int quantity;
}
