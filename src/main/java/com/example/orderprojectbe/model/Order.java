package com.example.orderprojectbe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Order
{
    @Id
    int num;
}
