package com.example.projectTwitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.projectTwitter.model.Hashtag;

/**
 * Interfaccia repository per l'entità Hashtag.
 * <p>
 * Questo repository estende  JpaRepository, fornendo funzionalità CRUD complete
 * per l'entità. Inoltre, definisce metodi personalizzati per operazioni specifiche
 * non coperte dall'implementazione standard di Spring Data JPA.
 * </p>
 */
@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
	
	@Query("SELECT DISTINCT h.testo FROM Hashtag h")
	List<String> trovaCategorie();
	
		
}
