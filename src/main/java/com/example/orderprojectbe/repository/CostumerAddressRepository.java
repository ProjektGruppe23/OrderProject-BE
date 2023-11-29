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
    /*@Query("SELECT new com.example.orderprojectbe.model.CostumerAddress(costumerAddress.city,costumerAddress.streetAddress,costumerAddress.extendedAddress,costumerAddress.postalCode) FROM CostumerAddress costumerAddress")
    Optional<CostumerAddress> findCostumerAddress(@Param("city") String city, @Param("streetAddress") String streetAddress,
                                 @Param("extendedAddress") String extendedAddress, @Param("postalCode") String postalCode);*/

    Optional<CostumerAddress> findCostumerAddressByCityAndStreetAddressAndExtendedAddressAndPostalCode(String city, String streetAddress, String extendedAddress, String postalCode);

}
