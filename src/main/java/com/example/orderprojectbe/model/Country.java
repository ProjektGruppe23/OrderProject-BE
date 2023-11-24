package com.example.orderprojectbe.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String countryId;
    private String countryName;
    @OneToMany(mappedBy = "country")
    private List<CostumerAddress> costumerAddress;
}
