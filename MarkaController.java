package com.example.Car_rent.Controller;

import com.example.Car_rent.Repository.MarkaRepository;
import com.example.Car_rent.Service.Marka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@CrossOrigin(origins = "http://localhost:3000")
public class MarkaController {

    private final MarkaRepository markaRepository;

    @Autowired
    public MarkaController(MarkaRepository markaRepository) {
        this.markaRepository = markaRepository;
    }

    @GetMapping
    public List<Marka> getAllBrands() {
        return markaRepository.findAll();
    }


    @GetMapping("/marki")
    public ResponseEntity<Marka> getMarkiByName(@RequestParam String name) {
     
        Marka marka = markaRepository.findByName(name);
     
        System.out.println(marka.getId());
        if (marka != null) {
            return ResponseEntity.ok(marka);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Marka> addBrand(@RequestBody Marka marka) {
        // Sprawdź, czy marka już istnieje w bazie danych
        Marka existingBrand = markaRepository.findByName(marka.getName());
   
        if (existingBrand != null||  marka.getName() == "") {
            // Jeśli marka już istnieje, zwróć konflikt
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Jeśli marka nie istnieje, dodaj nową markę
        Marka savedBrand = markaRepository.save(marka);
        return new ResponseEntity<>(savedBrand, HttpStatus.CREATED);
    }
}


