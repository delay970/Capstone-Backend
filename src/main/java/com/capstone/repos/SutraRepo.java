package com.capstone.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.models.Sutra;

public interface SutraRepo extends JpaRepository<Sutra, Integer>{

	List<Sutra> findByOrderByName();
	
	Optional<Sutra> findByName(String name);
	
}
