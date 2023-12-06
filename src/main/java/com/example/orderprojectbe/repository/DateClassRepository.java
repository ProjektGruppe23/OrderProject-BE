package com.example.orderprojectbe.repository;

import com.example.orderprojectbe.model.DateClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface DateClassRepository extends JpaRepository<DateClass, Integer>
{
        Optional<DateClass> findDateClassByDate(String date);
}
