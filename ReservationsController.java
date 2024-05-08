package com.example.Car_rent.Controller;

import com.example.Car_rent.Repository.CarRepository;
import com.example.Car_rent.Repository.ClientRepository;
import com.example.Car_rent.Repository.ReservationsRepository;
import com.example.Car_rent.Service.Car;
import com.example.Car_rent.Service.Reservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationsRepository reservationsRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;

    @Autowired
    public ReservationsController(ReservationsRepository reservationsRepository, ClientRepository clientRepository, CarRepository carRepository) {
        this.reservationsRepository = reservationsRepository;
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reservations>> getAllReservations() {
        List<Reservations> reservations = reservationsRepository.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


    public boolean doesCarExist(Long car_id) {
        return carRepository.existsById(car_id);
    }

    @PostMapping
    public ResponseEntity<String> addReservation(@RequestBody Reservations reservation) {
        String reservationDate = reservation.getReservationDate();
        String returnDate = reservation.getReturnDate();

        // Sprawdzenie, czy data początkowa jest późniejsza niż data zakończenia
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(reservationDate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);

            if (startDate.after(endDate)) {
                return new ResponseEntity<>("Data początkowa nie może być późniejsza niż data zakończenia.", HttpStatus.BAD_REQUEST);
            }
        } catch (ParseException e) {
            throw new RuntimeException("Błąd parsowania daty.", e);
        }

        Long carId = reservation.getCar().getId();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newStartDate = dateFormat.parse(reservationDate);
            Date newEndDate = dateFormat.parse(returnDate);

            // Pobierz wszystkie rezerwacje dla danego samochodu
            List<Reservations> existingReservations = reservationsRepository.findByCarId(carId);

            // Sprawdź, czy nowa rezerwacja koliduje z istniejącymi
            for (Reservations existingReservation : existingReservations) {
                Date existingStartDate = dateFormat.parse(existingReservation.getReservationDate());
                Date existingEndDate = dateFormat.parse(existingReservation.getReturnDate());

                // Sprawdź czy istniejąca rezerwacja koliduje z nową rezerwacją
                if (newStartDate.before(existingEndDate) && newEndDate.after(existingStartDate)) {
                    return new ResponseEntity<>("Nowa rezerwacja koliduje z istniejącą rezerwacją.", HttpStatus.BAD_REQUEST);
                }
            }

            // Tutaj dodaj kod do zapisywania nowej rezerwacji, ponieważ nie koliduje z żadną istniejącą
            // Na przykład, możesz użyć metody save() z repozytorium
            reservationsRepository.save(reservation);

            return new ResponseEntity<>("Rezerwacja została dodana.", HttpStatus.OK);

        } catch (ParseException e) {
            throw new RuntimeException("Błąd parsowania daty.", e);
        }
    }


        /*
        System.out.println(reservationDate);
        System.out.println(returnDate);
        Long carId = reservation.getCar().getId();
        Long clientId = reservation.getClient().getclient_id();
        System.out.println(clientId);
        // Sprawdź istnienie samochodu o podanym carId
        boolean carExists = doesCarExist(carId);
        if (!carExists) {
            return new ResponseEntity<>("Samochód o podanym ID nie istnieje.", HttpStatus.BAD_REQUEST);
        }

        // Sprawdź istnienie klienta o podanym clientId
        boolean clientExists = doesClientExist(clientId);
        if (!clientExists) {
            return new ResponseEntity<>("Klient o podanym ID nie istnieje.", HttpStatus.BAD_REQUEST);
        }



        // Kontynuuj dodawanie rezerwacji
        Reservations savedReservation = reservationsRepository.save(reservation);
        if (savedReservation != null) {
            return new ResponseEntity<>("Rezerwacja została dodana.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Wystąpił błąd podczas dodawania rezerwacji.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        *
         */


    private boolean doesClientExist(Long clientId) { return clientRepository.existsById(clientId);
    }


    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long reservationId) {
        Optional<Reservations> reservationOptional = reservationsRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Reservations reservation = reservationOptional.get();
        reservationsRepository.delete(reservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
