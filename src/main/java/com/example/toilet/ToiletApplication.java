package com.example.toilet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin()
public class ToiletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToiletApplication.class, args);
	}

}
