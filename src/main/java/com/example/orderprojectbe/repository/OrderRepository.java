package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>
{
    @Query("SELECT new com.example.orderprojectbe.model.Order(order.orderApiId, order.vendor) FROM Order order")
    List<Order> findOrderApiIdAndVendor(@Param("orderId") String orderId, @Param("vendor") Vendor vendor);


}
