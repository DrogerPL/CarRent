package com.example.Car_rent.Controller;

import com.example.Car_rent.Repository.ClientRepository;
import com.example.Car_rent.Service.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<String> addClient(@RequestBody Client client) {
        if (client.getName() == null || client.getSurname() == null || client.getSurname() == "" || client.getName() == "") {
            return ResponseEntity.badRequest().body("Nazwa i nazwisko klienta nie mogą być puste.");
        }

        clientRepository.save(client);

        return ResponseEntity.ok("Klient został dodany.");
    }


    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable long clientId) {
        clientRepository.deleteById(clientId);
    }

    @GetMapping("/{client_id}")
    public ResponseEntity<?> getClientById(@PathVariable long client_id) {
        Optional<Client> clientOptional = clientRepository.findById(client_id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
