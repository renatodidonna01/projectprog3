package com.example.projectTwitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.projectTwitter.model.Hashtag;


@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
	
	@Query("SELECT DISTINCT h.testo FROM Hashtag h")
	List<String> trovaCategorie();
	
	

	
	
	
	
	
	
	
}
