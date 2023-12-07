package com.example.orderprojectbe.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;
    private String countryName;
    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private List<CostumerAddress> costumerAddress;

    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private List<ArchivedOrder> archivedOrders;

    public String getReverbCountrySubstring(String displayLocation)
    {
        String[] substring = displayLocation.split(",");
        String countryName = substring[substring.length-1];
        countryName = countryName.trim();
        return countryName;

    }

    public int getCountryId()
    {
        return countryId;
    }

    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public List<CostumerAddress> getCostumerAddress()
    {
        return costumerAddress;
    }

    public void setCostumerAddress(List<CostumerAddress> costumerAddress)
    {
        this.costumerAddress = costumerAddress;
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
