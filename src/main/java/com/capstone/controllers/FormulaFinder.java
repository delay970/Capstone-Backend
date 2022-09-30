package com.capstone.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.models.NGram;
import com.capstone.models.Sutra;
import com.capstone.services.FileCleanerService;
import com.capstone.services.FileHandlerService;
import com.capstone.services.FormulaFinderService;
import com.capstone.services.SutraService;


@RestController
@RequestMapping("formula")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class FormulaFinder {

	@Autowired
	FileHandlerService fileHandlerService;
	
	@Autowired
	FormulaFinderService formulaFinderService;
	
	@Autowired
	FileCleanerService fileCleanerService;
	
	@Autowired
	SutraService sutraService;
	
	@GetMapping("/{id1}/{id2}")
    public ResponseEntity<Object[]> findFormulas(@PathVariable int id1, @PathVariable int id2){

    	Sutra sutra1 = sutraService.findById(id1).get();
    	Sutra sutra2 = sutraService.findById(id2).get();
		
		String context = fileCleanerService.cleanContext(sutra1.context);
		String context2 = fileCleanerService.cleanContext(sutra2.context);

		Map<String, NGram> map = formulaFinderService.findFormulas(context, context2, 3);
		//Map<String, NGram> map = formulaFinder.findRepetitions(context, 3);
		
		List<NGram> results = new ArrayList<>(map.values());
		Collections.sort(results, Collections.reverseOrder());
		
		formulaFinderService.cleanResults(results);
		
		Object[] temp = results.toArray();
		
		System.out.println("formulas found");
		
        return ResponseEntity.ok(temp);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object[]> findRepatitions(@PathVariable int id){

    	Sutra sutra = sutraService.findById(id).get();
		
		String context = fileCleanerService.cleanContext(sutra.context);

		Map<String, NGram> map = formulaFinderService.findRepetitions(context, 3);
		
		List<NGram> results = new ArrayList<>(map.values());
		Collections.sort(results, Collections.reverseOrder());
		
		formulaFinderService.cleanResults(results);
		
		Object[] temp = results.toArray();
		
		System.out.println("formulas found");
		
        return ResponseEntity.ok(temp);
    }
	
}
