package com.example.orderprojectbe.DTO;

import lombok.Data;

@Data
public class AnalyticsInfoDTO
{
    private String countryName;
    private String vendorName;
    private String productName;
    private Double price;

    public AnalyticsInfoDTO()
    {
    }

    public AnalyticsInfoDTO(String countryName, String vendorName, String productName, Double price)
    {
        this.countryName = countryName;
        this.vendorName = vendorName;
        this.productName = productName;
        this.price = price;
    }
}
