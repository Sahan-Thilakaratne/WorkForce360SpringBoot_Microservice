package com.example.WorkForce360SpringBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users") // avoid reserved keyword
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;

    //@Column(unique = true)
    @Email(message = "Invalid Email type")
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;



    public String getEmail(){

        return  email;
    }

    public String getPassword(){
        return password;
    }

    public void setRole (Role role){
        this.role = role;
    }

    public Role getRole(){
        return role;
    }
}
