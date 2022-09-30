package com.capstone.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.models.Sutra;
import com.capstone.services.SutraService;

@RestController
@RequestMapping("sutra")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class SutraController {
	@Autowired
	SutraService sutraService;
	
	@GetMapping()
	public ResponseEntity<List<Sutra>> getSutras(){
		return ResponseEntity.ok(sutraService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Sutra>> getOneSutra(@PathVariable int id){
		Optional<Sutra> result = sutraService.findById(id);
		if(result.isPresent()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().build();
	}	
}
