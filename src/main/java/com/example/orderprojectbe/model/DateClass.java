package com.example.orderprojectbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class DateClass
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateId;

    private String date;

    @OneToMany(mappedBy = "date")
    @JsonBackReference
    private List<Order> orders;

    public String changeStringDateToDateFormat(String dateString)
    {
        String datePart = dateString.split("T")[0];

        return datePart;
    }

    public int getDateId()
    {
        return dateId;
    }

    public void setDateId(int dateId)
    {
        this.dateId = dateId;
    }

    public String getDate()
    {
        return  date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }
}
