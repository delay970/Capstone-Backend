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

import com.capstone.models.Text;
import com.capstone.services.FileCleanerService;
import com.capstone.services.TextService;

@RestController
@RequestMapping("text")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class TextController {
	@Autowired
	TextService textService;
	
	@Autowired
	FileCleanerService fileCleanerService;
	
	@GetMapping()
	public ResponseEntity<List<Text>> getTexts(){
		return ResponseEntity.ok(textService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Text>> getOneText(@PathVariable int id){
		Optional<Text> result = textService.findById(id);
		if(result.isPresent()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}/cleaned")
	public ResponseEntity<Text> getOneCleanedText(@PathVariable int id){
		Optional<Text> result = textService.findById(id);
		if(result.isPresent()) {
			Text cleanedText = new Text();
			cleanedText.setName(result.get().getName());
			cleanedText.setContext(fileCleanerService.cleanContext(result.get().getContext()));
			return ResponseEntity.ok(cleanedText);
		}
		return ResponseEntity.badRequest().build();
	}
}
