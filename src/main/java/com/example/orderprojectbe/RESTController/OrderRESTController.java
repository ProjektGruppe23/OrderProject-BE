package com.example.orderprojectbe.RESTController;

import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.OrderRepository;
import com.example.orderprojectbe.service.reverb.ReverbApiServiceGetAllOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class OrderRESTController
{
    @Autowired
    ReverbApiServiceGetAllOrders reverbApiServiceGetAllOrders;

    @Autowired
    OrderRepository orderRepository;


    @GetMapping("/getorders")
    public List<Order> getOrders()
    {
        List<Order> lstOrders = reverbApiServiceGetAllOrders.getAllOrders();
        return lstOrders;
    }
}
