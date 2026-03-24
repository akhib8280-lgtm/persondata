package com.example.PersonDetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonDetailsApplication.class, args);
        System.out.print("Helo world");
	}

}
