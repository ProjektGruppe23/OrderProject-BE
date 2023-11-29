package com.example.orderprojectbe.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.List;

@Data
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;
    private String countryName;
    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private List<CostumerAddress> costumerAddress;

    public String getReverbCountrySubstring(String displayLocation)
    {
        String[] substring = displayLocation.split(",");
        String countryName = substring[substring.length-1];
        countryName = countryName.trim();
        return countryName;

    }
}
