package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer>
{
    Optional<Country> findCountryByCountryName(String name);

}
