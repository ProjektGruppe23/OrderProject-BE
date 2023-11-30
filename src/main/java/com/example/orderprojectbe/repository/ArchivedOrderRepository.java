package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.ArchivedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivedOrderRepository extends JpaRepository<ArchivedOrder, Integer>
{
}
