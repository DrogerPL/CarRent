package com.example.Car_rent.Controller;
import com.example.Car_rent.Repository.CarRepository;
import com.example.Car_rent.Repository.ClientRepository;
import com.example.Car_rent.Repository.MarkaRepository;
import com.example.Car_rent.Repository.RentalsRepository;
import com.example.Car_rent.Service.Car;
import com.example.Car_rent.Service.Client;
import com.example.Car_rent.Service.Marka;
import com.example.Car_rent.Service.Rentals;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rentals")
public class RentalsController {

    private final RentalsRepository rentalsRepository;
    private final ClientRepository clientRepository;

    private final CarRepository carRepository;

    @Autowired
    MarkaRepository markaRepository;
    @Autowired
    public RentalsController(RentalsRepository rentalsRepository, ClientRepository clientRepository, CarRepository carRepository) {
        this.rentalsRepository = rentalsRepository;
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
    }

    @GetMapping
    public ResponseEntity<List<Rentals>> getAllRentals() {
        List<Rentals> rentals = rentalsRepository.findAll();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/{rental_id}")
    public ResponseEntity<Rentals> getRentalById(@PathVariable long rental_id) {
        Rentals rental = rentalsRepository.findById(rental_id).orElse(null);
        if (rental == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rentals> addRental(@RequestBody Rentals rental) {
        // Sprawdź czy marka jest już zapisana
        if (rental.getCar() != null && rental.getCar().getMarka() != null) {
            Marka existingMarka = markaRepository.findByName(rental.getCar().getMarka().getName());
            if (existingMarka == null) {
                // Marka nie jest jeszcze zapisana, zapisz ją
                Marka savedMarka = markaRepository.save(rental.getCar().getMarka());
                rental.getCar().setMarka(savedMarka);
            } else {
                // Marka już istnieje, ustaw ją w obiekcie Car
                rental.getCar().setMarka(existingMarka);
            }
        }

        // Zapisz obiekt Rentals
        Rentals savedRental = rentalsRepository.save(rental);
        return new ResponseEntity<>(savedRental, HttpStatus.CREATED);
    }




    @DeleteMapping("/{rental_id}")
    public ResponseEntity<Void> deleteRental(@PathVariable long rental_id) {
        Rentals rental = rentalsRepository.findById(rental_id).orElse(null);
        if (rental == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rentalsRepository.delete(rental);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/client/{client_id}")
    public ResponseEntity<List<Rentals>> getRentalsByClientId(@PathVariable long client_id) {
        Optional<Client> clientOptional = clientRepository.findById(client_id);
        if (clientOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client client = clientOptional.get();
        List<Rentals> rentals = rentalsRepository.findByClient(client);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }


    @PutMapping("/{rental_id}")
    @Transactional
    public ResponseEntity<Rentals> returnRental(@PathVariable long rental_id) {
        Rentals rental = rentalsRepository.findById(rental_id).orElse(null);
        if (rental == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        rental.setRelease_status("Zwrócony");
        rental.setDelivery_date(Instant.now().toString());

        Car car = rental.getCar();
        car.setAvailable(true);

        // Najpierw zapisz obiekt Car
        carRepository.save(car);

        // Teraz możesz zapisać obiekt Rentals

/*
        Client client = savedRental.getClient();
        System.out.println(client.getclient_id());
        System.out.println(client.getName());
        client.setPriceToPay(savedRental.calculatePriceToPay());
        clientRepository.save(client);
        */
        rental.setPriceToPay(rental.calculatePriceToPay());
        Rentals savedRental = rentalsRepository.save(rental);
        return new ResponseEntity<>(savedRental, HttpStatus.OK);
    }





}