package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String>
{
}
