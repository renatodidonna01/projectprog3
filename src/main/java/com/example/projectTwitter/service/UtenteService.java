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
	

	public Tweet pubblicaTweet(String username, Tweet tweet) {
		
		if (tweet.getTesto() == null || tweet.getTesto().isEmpty()) {
	        throw new TweetValidationException("Devi scrivere qualcosa per inviare un tweet");
	    }
	    if (tweet.getTesto().length() > 140) {
	        throw new TweetValidationException("Il tweet non può superare i 140 caratteri.");
	    }
	    
	    
		return tweetRepository.save(tweet);	
	} 
	
	

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

  

    public List<Utente> cercaUtenti(String query, String usernameEscluso) {
        return utenteRepository.RicercaProfilo(query, usernameEscluso);
    }
    
    
    
    public List<Tweet> RicercaTweet(String query) {
        return tweetRepository.findByTestoContainingIgnoreCase(query);
    } 
    
    
    
    public Utente getUtenteLoggato(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return null; // Nessun utente loggato
        }
        return trovaUtentePerUsername(username);
        }
    
    
    public boolean verificaRuoloUtente(Utente utente) {
        return Utente.Role.ADMIN.equals(utente.getRuolo());
    }
    
    
    public List<Utente> cercaUtentiConQuery(String query, String usernameEscluso) {
        if (query != null && !query.trim().isEmpty()) {
            return cercaUtenti(query, usernameEscluso);
        } else {
            return Collections.emptyList();
        }
    }


	public List<Utente> utentiAttiviTweet() {
		return utenteRepository.findUsersWithMostTweets();	
	}
    
    
    

    
    
    
    
    
    
	}
