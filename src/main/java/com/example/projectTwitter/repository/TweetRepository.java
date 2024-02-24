package com.example.projectTwitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectTwitter.model.Tweet;


@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> { 
	
	List<Tweet> findByUtenteUsername(String username);
	
	List<Tweet> findByHashtagTesto(String categoria);
	
	//cerco tweet che contengono la sequenza di caratteri di query
    List<Tweet> findByTestoContainingIgnoreCase(String query);

    
    
    
}
