package com.example.orderprojectbe.RESTController;

import com.example.orderprojectbe.model.ArchivedOrder;
import com.example.orderprojectbe.repository.ArchivedOrderRepository;
import com.example.orderprojectbe.repository.CostumerAddressRepository;
import com.example.orderprojectbe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ArchivedOrderRestController
{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CostumerAddressRepository CostumerAddressRepository;

    @Autowired
    ArchivedOrderRepository archivedOrderRepository;

    @GetMapping("getArchivedOrdersByCountry")
    public List<ArchivedOrder> getArchivedOrdersByCountry()
    {
        List<ArchivedOrder> lstArchivedOrders = archivedOrderRepository.findArchivedOrdersByCountry();
        return lstArchivedOrders;
    }

}
