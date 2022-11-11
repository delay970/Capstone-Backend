package com.capstone.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.models.FileCleanerProfile;
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
	
	@GetMapping
	public ResponseEntity<List<Text>> getTexts(){
		fileCleanerService.getDefaultProfile(); //this ensuures that the default profile is created
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
			cleanedText.setContext(fileCleanerService.cleanContext(result.get().getContext(), result.get().getProfile()));
			return ResponseEntity.ok(cleanedText);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping
	public ResponseEntity<Text> putText(@RequestBody Text text){
		Text result = textService.updateText(text);
		
		if(result == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Optional<FileCleanerProfile>> getCleanerProfile(){
		Optional<FileCleanerProfile> result = fileCleanerService.getDefaultProfile();
		if(result.isPresent()) {
			return ResponseEntity.ok(result);
		}
		System.out.println("error");
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/allprofiles")
	public ResponseEntity<List<FileCleanerProfile>> getAllCleanerProfiles(){
		List<FileCleanerProfile> results = fileCleanerService.getAllProfiles();
		return ResponseEntity.ok(results);
	}
	
	@PutMapping("/profile")
	public ResponseEntity<FileCleanerProfile> updateCleanerProfile(@RequestBody FileCleanerProfile profile){
		FileCleanerProfile result = fileCleanerService.updateProfile(profile);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/profile")
	public ResponseEntity<FileCleanerProfile> createCleanerProfile(@RequestBody FileCleanerProfile profile){
		FileCleanerProfile result = fileCleanerService.createProfile(profile);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/{name}")
    public ResponseEntity<Boolean> SaveFile(@PathVariable("name") String name, @RequestBody String fileBody){        
        
		FileCleanerProfile profile = fileCleanerService.getDefaultProfile().get();
		Text text = new Text(name, fileBody, profile);
        if(textService.findByName(name).isEmpty()) {
        	textService.storeText(text);
        }
        return ResponseEntity.ok(true);
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Boolean> clearFiles(){

    	textService.clearFiles();
        
        return ResponseEntity.ok(true);
    }
    
    @DeleteMapping("/clear/{id}")
    public ResponseEntity<Boolean> removeFile(@PathVariable("id") int id){

    	textService.removeFile(id);
        return ResponseEntity.ok(true);
    }
}
