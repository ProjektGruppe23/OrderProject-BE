package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String>
{
}
