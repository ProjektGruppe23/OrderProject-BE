package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.ArchivedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivedOrderRepository extends JpaRepository<ArchivedOrder, Integer>
{
    List<ArchivedOrder> findArchivedOrderByApiIdAndVendor(String apiId, String vendor);

    /*
    public void checkIfOrderAlreadyExists(List<Order> orders, List<ArchivedOrder> archivedOrders, Order order) {

    List<Order> ordersCheckList = orderRepository.findOrderByOrderApiIdAndVendor(order.getOrderApiId(), order.getVendor().getVendorName());

    List<ArchivedOrder> archivedOrdersCheckList = archivedOrderRepository.findOrderByOrderApiIdAndVendor(order.getOrderApiId(), order.getVendor().getVendorName());

    if (!ordersCheckList.isEmpty() || !archivedOrdersCheckList.isEmpty())
    {
        System.out.println("Order with ID " + order.getOrderApiId() + " and vendor " + order.getVendor().getVendorName(); + " already exists. Skipping...");
    } else
    {
        orders.add(order);
        System.out.println("Order with ID " + order.getOrderApiId() + " and vendor " + order.getVendor().getVendorName() + " inserted.");
    }
}
     */
}
