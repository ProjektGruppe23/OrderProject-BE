package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.ArchivedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchivedOrderRepository extends JpaRepository<ArchivedOrder, Integer>
{
    List<ArchivedOrder> findArchivedOrderByApiIdAndVendor(String apiId, String vendor);
    List<ArchivedOrder> findArchivedOrdersByCountry(String country);
}
