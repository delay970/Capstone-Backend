package com.capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.models.Text;
import com.capstone.services.FileCleanerService;
import com.capstone.services.FileHandlerService;
import com.capstone.services.TextService;




@RestController
@RequestMapping("file")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class FileHandler {

	@Autowired
	FileHandlerService fileHandlerService;
	
	@Autowired
	TextService textService;
	
    @PostMapping("/{name}")
    public ResponseEntity<Boolean> SaveFile(@PathVariable("name") String name, @RequestBody String fileBody){        
        Text text = new Text(name, fileBody);
        textService.storeText(text);
        return ResponseEntity.ok(true);
    }
    
    @GetMapping("/clear")
    public ResponseEntity<Boolean> clearFiles(){

        fileHandlerService.clearFiles();
        
        return ResponseEntity.ok(true);
    }
}
