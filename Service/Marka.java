package com.example.Car_rent.Service;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Marka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marka_id")
    private Long id;




    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "marka")
    private List<Car> cars;




    public Marka() {

    }

    public Marka(String name) {
        this.name = name;
    }

    public Marka(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Getters and setters
    // ...

    // Constructors
    // ...

    // Other methods if needed
    // ...
}

