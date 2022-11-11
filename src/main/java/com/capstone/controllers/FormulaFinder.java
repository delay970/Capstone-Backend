package com.capstone.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.models.NGram;
import com.capstone.models.Text;
import com.capstone.services.FileCleanerService;
import com.capstone.services.FileHandlerService;
import com.capstone.services.FormulaFinderService;
import com.capstone.services.TextService;

@RestController
@RequestMapping("formula")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class FormulaFinder {

	@Autowired
	FileHandlerService fileHandlerService;

	@Autowired
	FormulaFinderService formulaFinderService;

	@Autowired
	FileCleanerService fileCleanerService;

	@Autowired
	TextService textService;

	@PostMapping("/{id1}/{id2}")
	public ResponseEntity<Object[]> findFormulas(@PathVariable int id1, @PathVariable int id2,
			@RequestBody int minSize) {

		Text text1 = textService.findById(id1).get();
		Text text2 = textService.findById(id2).get();

		String context = fileCleanerService.cleanContext(text1.context, text1.getProfile());
		String context2 = fileCleanerService.cleanContext(text2.context, text2.getProfile());

		Map<String, NGram> map = formulaFinderService.findFormulas(context, context2, minSize);

		List<NGram> results = new ArrayList<>(map.values());
		Collections.sort(results, Collections.reverseOrder());

		formulaFinderService.cleanResults(results);

		return ResponseEntity.ok(results.toArray());
	}

	@PostMapping("/{id}")
	public ResponseEntity<Object[]> findRepatitions(@PathVariable int id, @RequestBody int minSize) {

		Text text = textService.findById(id).get();

		String context = fileCleanerService.cleanContext(text.context, text.getProfile());

		Map<String, NGram> map = formulaFinderService.findRepetitions(context, minSize);

		List<NGram> results = new ArrayList<>(map.values());
		Collections.sort(results, Collections.reverseOrder());

		formulaFinderService.cleanResults(results);

		return ResponseEntity.ok(results.toArray());
	}

}
