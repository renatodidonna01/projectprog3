package com.example.projectTwitter.service;

import org.springframework.stereotype.Service;

import com.example.projectTwitter.exception.TweetValidationException;
import com.example.projectTwitter.exception.UtenteNotFoundException;
import com.example.projectTwitter.model.Hashtag;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.model.Utente.Role;
import com.example.projectTwitter.repository.HashtagRepository;
import com.example.projectTwitter.repository.TweetRepository;
import com.example.projectTwitter.repository.UtenteRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Servizio per la gestione degli utenti nella piattaforma  Twitter.
 * 
 * Questo servizio offre funzionalità per la registrazione di nuovi utenti, la ricerca e gestione degli utenti
 * Utilizza i repository {@link UtenteRepository}, {@link TweetRepository}, e {@link HashtagRepository}
 * per interagire con il database. Inoltre, gestisce l'hashing delle password degli utenti attraverso {@link PasswordEncoder}.

 */
@Service
public class UtenteService {
	
//utilizziamo la nostra interfaccia repository
    private final UtenteRepository utenteRepository;
    private final TweetRepository tweetRepository;
    private final HashtagRepository hashtagRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository,TweetRepository tweetRepository,HashtagRepository hashtagRepository,PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.tweetRepository=tweetRepository;
        this.hashtagRepository=hashtagRepository;
        this.passwordEncoder=passwordEncoder;
    }

    
    public Utente registraNuovoUtente(Utente utente) {
        // Controlla se l'email è già registrata
    	 Utente existingEmailUser = utenteRepository.findByEmail(utente.getEmail());
    	    if (existingEmailUser != null) {
    	        throw new RuntimeException("L'email è già registrata");
    	    }

    	    // Controlla se l'username è già registrato
    	    Utente existingUsernameUser = utenteRepository.findByUsername(utente.getUsername());
    	    if (existingUsernameUser != null) {
    	        throw new RuntimeException("L'username è già registrato");
    	    }

        // Esegui l'hash della password prima di salvarla nel database
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        
        //i nuovi iscritti sono tutti normali utenti 
        utente.setRuolo(Role.USER);

        // Salva l'utente nel database
        return utenteRepository.save(utente);
    }
    
    
        
        
    public Utente trovaUtentePerUsername(String username) {
        return utenteRepository.findById(username)
        .orElse(null);
    }
    
    
    
    public Hashtag trovaHashtagPerId(int id) {
        return hashtagRepository.findById(id).orElse(null);
    }
    
    
    
    public List<Utente> trovaTuttiUtenti() {
        return utenteRepository.findAll();
    }
    
    public List<Hashtag> trovaTuttiHashtag() {
        return hashtagRepository.findAll();
    }
    
    
    public Utente salvaUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    public void eliminaUtentePerUsername(String username) {
        utenteRepository.deleteById(username);
    }

    public List<Tweet> trovaTuttiTweetUtente(String username) {
        return tweetRepository.findByUtenteUsername(username);
    } 
    
    
 
      
	public List<Utente> trovaSeguaciUtente(String username) {
		return utenteRepository.findByUtentes1Username(username);
	}
    
    
	public List <Utente> trovaSeguitiUtente(String username)
	{
		return utenteRepository.findByUtentes2Username(username);
	}
	

	
	/**
	 * Un utente (follower) inizia a seguire un altro utente (followed).
	 * Questa azione aggiunge ciascun utente alla rispettiva lista di utenti che stanno seguendo o che vengono seguiti.
	 * Entrambe le entità utente vengono aggiornate nel database per riflettere questa nuova relazione.
	 * 
	 * @param followerUsername Lo username dell'utente che desidera seguire l'altro utente.
	 * @param followedUsername Lo username dell'utente che deve essere seguito.
	 * @throws UtenteNotFoundException se il follower o l'utente da seguire non viene trovato nel database.
	 */	
	public void seguiUtente(String followerUsername, String followedUsername) {
	    Utente follower = utenteRepository.findByUsername(followerUsername);
	    if (follower == null) {
	    throw new UtenteNotFoundException("Follower con username " + followerUsername + " non trovato.");
	    }
	    
	    Utente followed = utenteRepository.findByUsername(followedUsername);
	    if (followed == null) {
	        throw new UtenteNotFoundException("Followed con username " + followedUsername + " non trovato.");
	    }   

	    // Aggiungi followed alla lista dei seguiti di follower
	    follower.getUtentes1().add(followed);
	    
	    //aggiungi follower alla lista del followed
	    followed.getUtentes2().add(follower);

	    // Aggiorna il follower nel repository
	    utenteRepository.save(follower);
	    utenteRepository.save(followed);
	}
	
	/**
	 * Consente a un utente di smettere di seguire un altro utente. Rimuove l'utente seguito dalla lista
	 * dei seguiti del follower e rimuove il follower dalla lista dei seguaci dell'utente seguito.
	 * Salva le modifiche degli utenti nel database per mantenere aggiornata la relazione di seguito.
	 *
	 * @param followerUsername Lo username dell'utente che intende smettere di seguire l'altro utente (follower).
	 * @param followedUsername Lo username dell'utente che non sarà più seguito (followed).
	 * @throws UtenteNotFoundException se il follower o l'utente seguito non esiste nel database.
	 */	
	public void defollowaUtente(String followerUsername, String followedUsername) {
	    Utente follower = utenteRepository.findByUsername(followerUsername);
	    if (follower == null) {
 		   throw new UtenteNotFoundException("Follower con username " + followerUsername + " non trovato.");
 		    }
	    
	    Utente followed = utenteRepository.findByUsername(followedUsername);
	    if (followed == null) {
	        throw new UtenteNotFoundException("Followed con username " + followedUsername + " non trovato.");
	      }  

	    // Rimuovi follower alla lista dei seguiti di follower
	    follower.getUtentes1().remove(followed);
	    
	    //rimuovi il followed dalla lista del followed
	    followed.getUtentes2().remove(follower);

	    // Rimuovi il follower nel repository
	    utenteRepository.save(follower);
	    utenteRepository.save(followed);
	   	    
	}

  
	/**
	 * Cerca utenti in base a una stringa di query fornita, escludendo un username specifico dai risultati.
	 *
	 * @param query La stringa di ricerca per filtrare gli utenti.
	 * @param usernameEscluso L'username da escludere dalla ricerca.
	 * @return Una lista di {@link Utente} che corrisponde ai criteri di ricerca.
	 */	
    public List<Utente> cercaUtenti(String query, String usernameEscluso) {
        return utenteRepository.RicercaProfilo(query, usernameEscluso);
    }
    
    
    /**
     * Recupera l'utente attualmente loggato basandosi sui dati della sessione.
     *
     * @param request La richiesta HTTP da cui recuperare i dati della sessione.
     * @return L'{@link Utente} loggato, o null se nessun utente è loggato.
     */   
    public Utente getUtenteLoggato(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return null; // Nessun utente loggato
        }
        return trovaUtentePerUsername(username);
        }
    
 
    /**
     * Verifica se un utente ha il ruolo di amministratore.
     *
     * @param utente L'{@link Utente} da verificare.
     * @return true se l'utente è un amministratore, false altrimenti.
     */    
    public boolean verificaRuoloUtente(Utente utente) {
        return Utente.Role.ADMIN.equals(utente.getRuolo());
    }
    
  
    /**
     * Cerca utenti in base a una stringa di query fornita, escludendo un username specifico dai risultati,
     * solo se la stringa di query non è null o vuota.
     *
     * @param query La stringa di ricerca per filtrare gli utenti.
     * @param usernameEscluso L'username da escludere dalla ricerca.
     * @return Una lista di {@link Utente} che corrisponde ai criteri di ricerca, oppure una lista vuota se la query è null o vuota.
     */    
    public List<Utente> cercaUtentiConQuery(String query, String usernameEscluso) {
        if (query != null && !query.trim().isEmpty()) {
            return cercaUtenti(query, usernameEscluso);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Recupera una lista degli utenti con il maggior numero di tweet.
     *
     * @return Una lista di {@link Utente} ordinata in base al numero di tweet pubblicati.
     */    
	public List<Utente> utentiAttiviTweet() {
		return utenteRepository.findUsersWithMostTweets();	
	}
    
    
    

       
	}
