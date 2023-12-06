package com.example.orderprojectbe.RESTController;

import com.example.orderprojectbe.model.ArchivedOrder;
import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.ArchivedOrderRepository;
import com.example.orderprojectbe.repository.CostumerAddressRepository;
import com.example.orderprojectbe.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @GetMapping("/getinfotoanalyze")
    public ResponseEntity<List<String>> getInfoToAnalyse()
    {
        List<ArchivedOrder> archivedOrders = archivedOrderRepository.findAll();
        List<Order> orders = orderRepository.findAll();
        List<String> getInfoToAnalyse = new ArrayList<>();

        try
        {
            for ( Order order : orders )
            {
                String infoOrder = "Country: " + order.getCostumerAddress().getCountry().getCountryName() + " - vendor: " + order.getVendor().getVendorName() + " - product: " + order.getProductName() + " - price: " + order.getPrice();
                getInfoToAnalyse.add(infoOrder);
            }

            for ( ArchivedOrder order : archivedOrders )
            {
                String infoArchivedOrder = "Country: " + order.getCountry() + " - vendor: " + order.getVendor() + " - product: " + order.getProductName() + " - price: " + order.getPrice();
                getInfoToAnalyse.add(infoArchivedOrder);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok(getInfoToAnalyse);
    }
}
