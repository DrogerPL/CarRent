package com.example.Car_rent.Service;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class Rentals {
    @Id
    long rental_id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // ... reszta pól, getterów i setterów




    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    String rental_date;

    String delivery_date;

    String release_status;

    double priceToPay;

    public double getPriceToPay() {
        return priceToPay;
    }

    public void setPriceToPay(double priceToPay) {
        this.priceToPay = priceToPay;
    }

    public void setRental_id(long rental_id) {
        this.rental_id = rental_id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setRental_date(String rental_date) {
        this.rental_date = rental_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }



    public void setRelease_status(String release_status) {
        this.release_status = release_status;
    }



    public long getRental_id() {
        return rental_id;
    }

    public Client getClient() {
        return client;
    }

    public Car getCar() {
        return car;
    }

    public String getRental_date() {
        return rental_date;
    }

    public String getDelivery_date() {
        return delivery_date;
    }


    public String getRelease_status() {
        return release_status;
    }



    public Rentals(long rental_id, Client client , String rental_date, String delivery_date, String release_status, double priceToPay)
    {
        this.rental_id = rental_id;
        this.client = client;
        this.rental_date = rental_date;
        this.delivery_date = delivery_date;
        this.release_status = release_status;
        this.priceToPay = priceToPay;
    }

    public Rentals() {}


    public double calculatePriceToPay() {
        if (client != null && car != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
            //LocalDateTime rentalDate = LocalDateTime.parse(rentalDateStr, formatter);

            // Date rentalDate = dateFormat.parse(rental_date);
            // Date deliveryDate = dateFormat.parse(delivery_date);

            Instant rentalInstant = Instant.parse(rental_date.toString());
            Instant returnInstant = Instant.parse(delivery_date.toString());
            Duration duration = Duration.between(rentalInstant, returnInstant);
            System.out.println(duration.toHours());

            System.out.println(rental_date.toString());
            System.out.println(delivery_date.toString());
            System.out.println(duration.toHours());
            double morePay = 0;
            double hours = duration.toHours();
            double pricePerHour = car.getPricePerHour();
            double priceToPay = hours * pricePerHour;
            return priceToPay + morePay;
                /*
                long milliseconds = deliveryDate.getTime() - rentalDate.getTime();
                long hours = milliseconds / (1000 * 60 * 60); // konwersja z milisekund na godziny
                System.out.println(milliseconds);
                System.out.println(hours);
                System.out.println(car.getPricePerHour());
                System.out.println(deliveryDate.getTime());
                System.out.println(rentalDate.getTime());
                double pricePerHour = car.getPricePerHour();
                double priceToPay = hours * pricePerHour;
                //możliwe ze -1100
                //potencjalny błąd wynosi 217 godzin

                return priceToPay + morePay;

                 */
        }
        return 0;
    }



}
