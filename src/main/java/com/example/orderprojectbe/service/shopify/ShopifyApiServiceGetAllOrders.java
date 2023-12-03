package com.example.orderprojectbe.service.shopify;

import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.service.ApiService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopifyApiServiceGetAllOrders
{
    List<Order> loadOrdersFromApi();

}
