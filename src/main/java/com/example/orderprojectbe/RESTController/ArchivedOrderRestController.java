package com.example.orderprojectbe.RESTController;

import com.example.orderprojectbe.DTO.AnalyticsInfoDTO;
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
    public ResponseEntity<List<AnalyticsInfoDTO>> getInfoToAnalyse()
    {
        List<ArchivedOrder> archivedOrders = archivedOrderRepository.findAll();
        List<Order> orders = orderRepository.findAll();
        List<AnalyticsInfoDTO> getInfoToAnalyse = new ArrayList<>();

        try
        {
            for ( Order order : orders )
            {
                AnalyticsInfoDTO orderInfo = new AnalyticsInfoDTO(
                        order.getCostumerAddress().getCountry().getCountryName(),
                        order.getVendor().getVendorName(),
                        order.getProductName(),
                        order.getPrice()
                );
                getInfoToAnalyse.add(orderInfo);
            }

            for ( ArchivedOrder archivedOrder : archivedOrders )
            {
                AnalyticsInfoDTO orderInfo = new AnalyticsInfoDTO(
                        archivedOrder.getCountry(),
                        archivedOrder.getVendor(),
                        archivedOrder.getProductName(),
                        archivedOrder.getPrice()
                );
                getInfoToAnalyse.add(orderInfo);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok(getInfoToAnalyse);
    }
}
