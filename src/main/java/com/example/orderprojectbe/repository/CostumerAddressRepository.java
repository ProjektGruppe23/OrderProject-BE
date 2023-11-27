package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerAddressRepository extends JpaRepository<CostumerAddress, String>
{
}
