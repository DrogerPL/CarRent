package com.example.Car_rent.Controller;

import com.example.Car_rent.Repository.CarRepository;
import com.example.Car_rent.Repository.MarkaRepository;
import com.example.Car_rent.Service.Car;
import com.example.Car_rent.Service.Marka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarRepository markaRepository;

    @PostMapping
    public void addCar(@RequestBody Car car) {
        System.out.println(car.getMarka().getName());
        System.out.println(car.getMarka().getId());

        if(car.getModel() == null || car.getModel() == "" || car.getYearOfProduction() < 1900 || car.getYearOfProduction()>2024)
        {
            throw new IllegalArgumentException("Cena nie może być ujemna");
        }
       
        car.setPricePerDay(car.getPricePerHour()*24);
        // Walidacja wartości
        if (car.getPricePerHour() < 0 || car.getPricePerDay() < 0) {
            throw new IllegalArgumentException("Cena nie może być ujemna");
        }
    
        // Logika dodawania samochodu do bazy
        carRepository.save(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @DeleteMapping("/{car_id}")
    public void deleteCar(@PathVariable Long car_id) {
        carRepository.deleteById(car_id);
    }

    // W CarController lub w osobnej klasie serwisu



    @PutMapping("/{car_id}/available")
    public void updateCarAvailability(@PathVariable Long car_id, @RequestBody Map<String, Boolean> availabilityMap) {
        boolean available = availabilityMap.get("available");
        System.out.println("tutaj");

        Optional<Car> optionalCar = carRepository.findById(car_id);

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setAvailable(available);
            markaRepository.save(car);
            carRepository.save(car);
        } else {
            throw new IllegalArgumentException("Samochód o podanym identyfikatorze nie istnieje");
        }
    }

    @PutMapping("/{car_id}")
    public void updateCar(@PathVariable Long car_id, @RequestBody Car updatedCar) {
        Optional<Car> optionalCar = carRepository.findById(car_id);

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setStan(updatedCar.getStan());
            // Dodaj inne pola, które chcesz aktualizować, np. car.setPricePerHour(updatedCar.getPricePerHour());

            carRepository.save(car);
        } else {
            throw new IllegalArgumentException("Samochód o podanym identyfikatorze nie istnieje");
        }
    }


}
