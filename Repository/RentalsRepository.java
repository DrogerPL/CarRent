package com.example.Car_rent.Repository;

import com.example.Car_rent.Service.Client;
import com.example.Car_rent.Service.Rentals;
import com.example.Car_rent.Service.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalsRepository extends JpaRepository<Rentals, Long> {
    List<Rentals> findByClient(Client client);
}
