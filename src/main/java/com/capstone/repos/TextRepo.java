package com.capstone.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.models.Text;

public interface TextRepo extends JpaRepository<Text, Integer>{

	List<Text> findByOrderByName();
	
	Optional<Text> findByName(String name);
	
}
