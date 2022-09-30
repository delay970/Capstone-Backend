package com.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.models.Sutra;
import com.capstone.repos.SutraRepo;

@Service
public class SutraService {

	@Autowired
	SutraRepo sutraRepo;
	
	public Optional<Sutra> findById(int id) {
		
		return sutraRepo.findById(id);
	}
	
	public Optional<Sutra> findByName(String name) {
		
		return sutraRepo.findByName(name);
	}
	
	public List<Sutra> findAll() {
		
		return sutraRepo.findByOrderByName();
	}
	
	public Sutra storeSutra(Sutra sutra) {
			return sutraRepo.save(sutra);
	}
}
