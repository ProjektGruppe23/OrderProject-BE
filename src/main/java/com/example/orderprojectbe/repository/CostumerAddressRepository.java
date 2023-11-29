package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CostumerAddressRepository extends JpaRepository<CostumerAddress, Integer> {
    Optional<CostumerAddress> findCostumerAddressByCityAndStreetAddressAndExtendedAddressAndPostalCode(String city, String streetAddress, String extendedAddress, String postalCode);

}
