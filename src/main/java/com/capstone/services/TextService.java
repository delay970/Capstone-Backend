package com.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.models.Text;
import com.capstone.repos.FileCleanerProfileRepo;
import com.capstone.repos.TextRepo;

@Service
public class TextService {

	@Autowired
	TextRepo textRepo;
	
	@Autowired
	FileCleanerProfileRepo fileCleanerProfileRepo;
	
	public Optional<Text> findById(int id) {
		
		return textRepo.findById(id);
	}
	
	public Optional<Text> findByName(String name) {
		
		return textRepo.findByName(name);
	}
	
	public List<Text> findAll() {
		
		return textRepo.findByOrderByName();
	}
	
	public Text updateText(Text text) {
		Optional<Text> t = textRepo.findByName(text.getName());
		if(!t.isPresent()) {
			return null;
		}
		Text temp = t.get();
		text.setId(temp.getId());
		
		return textRepo.save(text);
	}
	
	public Text storeText(Text text) {
			return textRepo.save(text);
	}
	
	public void removeFile(int id) {
		textRepo.deleteById(id);
	}
	
	public void clearFiles() {
		textRepo.deleteAll();
	}
}
