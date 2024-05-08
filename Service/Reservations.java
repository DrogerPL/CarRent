package com.example.Car_rent.Service;

import com.example.Car_rent.Repository.ClientRepository;
import jakarta.persistence.*;


@Entity
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_reservation")
    long id_reservation;

    @Column(name = "reservation_date")
    String reservationDate;

    @Column(name = "return_date")
    String returnDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // ... reszta pól, getterów i setterów


    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public Reservations(String reservationDate, Client client, Car car, String returnDate) {

        this.reservationDate = reservationDate;
        this.client = client;
        this.car = car;
        this.returnDate = returnDate;
    }

    public Reservations() {}

    public void setId_reservation(long id_reservation) {
        this.id_reservation = id_reservation;
    }



    public long getId_reservation() {
        return id_reservation;
    }







}
