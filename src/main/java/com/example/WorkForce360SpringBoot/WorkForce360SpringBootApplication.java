package com.example.WorkForce360SpringBoot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class WorkForce360SpringBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WorkForce360SpringBootApplication.class, args);
	}


	public void run(String... args) throws Exception {
		// Auto-open Swagger UI
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI("http://localhost:8081/swagger-ui/index.html"));
		}
	}

}
