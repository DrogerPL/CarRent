package com.example.Car_rent.Repository;

import com.example.Car_rent.Service.Client;
import com.example.Car_rent.Service.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.apache.catalina.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}

