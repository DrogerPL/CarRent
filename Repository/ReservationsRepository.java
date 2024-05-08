package com.example.Car_rent.Repository;

import com.example.Car_rent.Service.Client;
import com.example.Car_rent.Service.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservations, Long> {

    List<Reservations> findByCarId(Long carId);
}
