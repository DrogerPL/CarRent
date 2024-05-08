package com.example.Car_rent.Controller;

import com.example.Car_rent.Repository.ClientRepository;
import com.example.Car_rent.Repository.UserRepository;
import com.example.Car_rent.Service.Client;
import com.example.Car_rent.Service.Marka;
import com.example.Car_rent.Service.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        Long clientId = user.getClient().getclient_id();  // Uwaga: Poprawiłem również getClient_id()

        System.out.println("CZEKAJ");
        // Sprawdzenie, czy istnieje klient o podanym client_id
        Optional<Client> existingClient = clientRepository.findById(clientId);

        // Sprawdzenie, czy istnieje użytkownik o podanym emailu
        Users existingUser = userRepository.findByEmail(user.getEmail());

        if (existingClient.isPresent()) {
            // Jeśli klient istnieje, sprawdź, czy istnieje użytkownik o podanym emailu
            if (existingUser != null) {
                // Jeśli istnieje, zwróć błąd
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik o podanym e-mailu już istnieje");
            } else {
                // Jeśli nie istnieje, zapisz użytkownika
                userRepository.save(user);
                return ResponseEntity.ok("Rejestracja udana");
            }
        } else {
            // Jeśli klient nie istnieje, zwróć błąd
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Klient o podanym ID nie istnieje");
        }
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<Long> getClientIdByEmail(@PathVariable String email) {
        // Wyszukaj użytkownika na podstawie e-maila w tabeli Users
        Users user = userRepository.findByEmail(email);
        System.out.println(email);
        System.out.println(user.getEmail());
     //   Client client = user.getClient();
       // System.out.println(client.getclient_id());
        if (user != null) {
            // Jeśli znaleziono użytkownika (clienta), zwróć jego client_id
            Client client = user.getClient();
            if (client != null) {
                System.out.println("TUTAJ");
                return ResponseEntity.ok(client.getclient_id());
            } else {
                System.out.println("Wysrało się");
                // Jeśli użytkownik (client) nie ma przypisanego klienta, zwróć błąd
                System.out.println("mozę tu");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1L);
            }
        } else {
            // Jeśli użytkownik nie został znaleziony, zwróć błąd 404 "Not Found"
            System.out.println("TUTAJ też się da");
            return ResponseEntity.notFound().build();
        }
    }





    @GetMapping
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Tutaj dodaj logikę weryfikacji użytkownika i hasła z bazą danych
        // Korzystaj z UserRepository


        Users user = userRepository.findByEmail(email);
      //  System.out.println(user.getPassword());
     //   System.out.println(user.getEmail());
        if (user != null && password.equals(user.getPassword())) {
            // Użytkownik poprawny, generuj token dostępu (do zaimplementowania)
            String accessToken = generateAccessToken(email);
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            return response;
        } else {
            // Użytkownik nieprawidłowy, zwróć błąd
            throw new RuntimeException("Invalid credentials");
        }
    }

    private boolean isValidUser(String email, String password) {
        // Tutaj dodaj logikę weryfikacji użytkownika i hasła z bazą danych
        // Na razie używam prostego warunku jako przykład
        return email.equals("example@example.com") && password.equals("password");
    }

    private String generateAccessToken(String email) {
        // Tutaj dodaj logikę generowania tokena dostępu
        // Możesz użyć bibliotek do generowania tokenów JWT
        // Na razie zwracam twardo zakodowany token jako przykład
        return "exampleAccessToken";
    }


    // @GetMapping("/users/email/{email}")
    //    public ResponseEntity<Long> getClientIdByEmail(@PathVariable String email) {
    @GetMapping("/getRoleInfo/{email}")
    private String getRoleInfo(@PathVariable String email) {
        System.out.println(email);
        Users user = userRepository.findByEmail(email);
        user.getRole();
        System.out.println(user.getRole());
        return user.getRole();
    }

}
