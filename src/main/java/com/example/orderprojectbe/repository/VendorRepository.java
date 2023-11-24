package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    Vendor findByVendorName(String vendorName);
}
