package com.example.Car_rent.Service;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long client_id;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Rentals> rentals;

    long tel_number;


    String surname;
    String name;




    public long getclient_id() {
        return client_id;
    }

    public void setclientId(long clientId) {
        this.client_id = clientId;
    }





    public String getSurname() {
        return surname;
    }

    public long getTel_number() {
        return tel_number;
    }



    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTel_number(long tel_number) {
        this.tel_number = tel_number;
    }




    public Client(String name, String surname, long tel_number) {
        this.name = name;
        this.surname = surname;
        this.tel_number = tel_number;

    }
    public Client() { } ;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
