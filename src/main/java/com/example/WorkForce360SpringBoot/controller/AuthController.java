package com.example.WorkForce360SpringBoot.controller;

import com.example.WorkForce360SpringBoot.model.Role;
import com.example.WorkForce360SpringBoot.model.User;
import com.example.WorkForce360SpringBoot.repository.UserRepository;
import com.example.WorkForce360SpringBoot.service.AuthService;
import com.example.WorkForce360SpringBoot.util.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private  JWTUtil jwtUtil;

    // Register user (no password encryption for simplicity)
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        if(user.getRole() == null){
            user.setRole(Role.EMPLOYEE);
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // ✅ Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Login and return JWT
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        /*User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);*/

        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(token);

    }

    // Manually validate token (optional)
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.getEmailFromToken(token);

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.ok("Token is valid for user: " + email);
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}
