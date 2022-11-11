package com.capstone.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.models.FileCleanerProfile;
import com.capstone.models.Text;

@Repository
public interface FileCleanerProfileRepo extends JpaRepository<FileCleanerProfile, Integer>{

	Optional<FileCleanerProfile> findByName(String name);
	
	List<FileCleanerProfile> findByOrderById();
}