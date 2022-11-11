package com.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.capstone.services.FileCleanerService;

@SpringBootApplication
public class CapstoneBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CapstoneBackendApplication.class, args);
	}

}
