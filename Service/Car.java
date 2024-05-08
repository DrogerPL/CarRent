package com.example.Car_rent.Service;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity(name = "cars")
public class Car {

    @Column(name = "model")
    String model;

    @Column(name = "available")
    boolean available = true; // Domyślna wartość to tru
    @Column(name = "year_of_production")
    int yearOfProduction;
    @Column(name = "price_per_hour")
    double pricePerHour;

    @Column(name = "price_per_day")
    double pricePerDay;


    @Column(name = "stan")
    String stan = "Dobry";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @OneToMany(mappedBy = "car")
    @JsonBackReference  //bez tego się wywala, nie wiadomo dlaczego
    private List<Rentals> rentals;



    @ManyToOne()
    @JoinColumn(name = "marka_id")
    private Marka marka;



    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getStan() {
        return stan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Rentals> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rentals> rentals) {
        this.rentals = rentals;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Marka getMarka() {
        return marka;
    }

    public void setMarka(Marka marka) {
        this.marka = marka;
    }

    public Car() {}

    public Car(String model, boolean available, int yearOfProduction, double pricePerHour, double pricePerDay, String stan, Long id, List<Rentals> rentals, Marka marka) {
        this.model = model;
        this.available = available;
        this.yearOfProduction = yearOfProduction;
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.stan = stan;
        this.id = id;
        this.rentals = rentals;
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }


}
