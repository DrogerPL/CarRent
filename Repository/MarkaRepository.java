package com.example.Car_rent.Repository;

import com.example.Car_rent.Service.Marka;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkaRepository extends JpaRepository<Marka, Long> {
    Marka findByName(String name);
}
