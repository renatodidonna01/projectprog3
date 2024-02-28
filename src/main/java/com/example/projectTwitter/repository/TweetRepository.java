package com.example.projectTwitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectTwitter.model.Tweet;
/**
 * Interfaccia repository per l'entità Tweet.
 * <p>
 * Questo repository estende  JpaRepository, fornendo funzionalità CRUD complete
 * per l'entità. Inoltre, definisce metodi personalizzati per operazioni specifiche
 * non coperte dall'implementazione standard di Spring Data JPA.
 * </p>
 */
@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> { 
	
	//recupera tweet username
	List<Tweet> findByUtenteUsername(String username);
	
	//recupera tweet contenenti un hashtag
	List<Tweet> findByHashtagTesto(String categoria);
	
	//cerco tweet che contengono una sequenza di caratteri di query ignorando maiuscole
    List<Tweet> findByTestoContainingIgnoreCase(String query);

        
}
