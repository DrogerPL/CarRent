package com.example.Car_rent.Repository;

import com.example.Car_rent.Service.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
