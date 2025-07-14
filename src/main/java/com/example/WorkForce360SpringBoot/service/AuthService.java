package com.example.WorkForce360SpringBoot.service;

import com.example.WorkForce360SpringBoot.dto.AuthRequest;
import com.example.WorkForce360SpringBoot.dto.AuthResponse;
import com.example.WorkForce360SpringBoot.dto.RegisterRequest;
import com.example.WorkForce360SpringBoot.model.Role;
import com.example.WorkForce360SpringBoot.model.User;
import com.example.WorkForce360SpringBoot.repository.UserRepository;
import com.example.WorkForce360SpringBoot.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    public String login (String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.getPassword().equals(password)){
            throw  new RuntimeException("Invlalid Password");
        }

        return jwtUtil.generateToken(email);
    }

    public boolean validate(String token){
        String email = jwtUtil.getEmailFromToken(token);
        return userRepository.existsByEmail(email);
    }



}
