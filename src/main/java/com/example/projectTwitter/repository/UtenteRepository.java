package com.example.projectTwitter.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.projectTwitter.model.Utente;
/**
 * Interfaccia repository per l'entità Utente.
 * <p>
 * Questo repository estende  JpaRepository, fornendo funzionalità CRUD complete
 * per l'entità. Inoltre, definisce metodi personalizzati per operazioni specifiche
 * non coperte dall'implementazione standard di Spring Data JPA.
 * </p>
 */	
	@Repository
	public interface UtenteRepository extends JpaRepository<Utente, String> {
		
		//trova lista following di un certo username 	
	    List<Utente> findByUtentes1Username(String username);
	    
	    //trova lista followers di un certo username
	    List <Utente> findByUtentes2Username(String username);
	    	    
	    	
		Utente findByEmail(String email);
			    
		Utente  findByUsername(String username);

		
	  
	  //cerca utenti con piu tweet
	  @Query("SELECT u FROM Utente u LEFT JOIN u.tweets t WHERE u.ruolo != 'ADMIN' GROUP BY u ORDER BY COUNT(t) DESC")
	   List<Utente> findUsersWithMostTweets();

	//ricerca utenti contenenti una stringa query escludendo il proprio username
	  @Query("SELECT u FROM Utente u WHERE u.username LIKE CONCAT('%', :query, '%') AND u.username != :usernameEscluso AND u.ruolo != 'ADMIN'")
	  List<Utente> RicercaProfilo(String query, String usernameEscluso);


	  
	}
	
 
