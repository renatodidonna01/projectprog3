package com.example.projectTwitter.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.projectTwitter.model.Utente;


	
	@Repository
	public interface UtenteRepository extends JpaRepository<Utente, String> {
		
			
	    List<Utente> findByUtentes1Username(String username);
	    
	    List <Utente> findByUtentes2Username(String username);
	    	    
	    	
		//Utente registraNuovoUtente(Utente utente);

		Utente findByEmail(String email);
			    
		Utente  findByUsername(String username);

		
	  List<Utente> findByUsernameContainingIgnoreCaseAndUsernameNot(String query, String usernameEscluso);
	  
	  
	  @Query("SELECT u FROM Utente u LEFT JOIN u.tweets t WHERE u.ruolo != 'ADMIN' GROUP BY u ORDER BY COUNT(t) DESC")
	   List<Utente> findUsersWithMostTweets();

	  
	  @Query("SELECT u FROM Utente u WHERE u.username LIKE CONCAT('%', :query, '%') AND u.username != :usernameEscluso AND u.ruolo != 'ADMIN'")
	  List<Utente> RicercaProfilo(String query, String usernameEscluso);


	  
	}
	
 
