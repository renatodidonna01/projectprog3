package com.example.projectTwitter.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.exception.TweetValidationException;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.repository.TweetRepository;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UtenteService utenteService;

    @Autowired
    public TweetService(TweetRepository tweetRepository, UtenteService utenteService) {
        this.tweetRepository = tweetRepository;
        this.utenteService = utenteService;
    }

    /**
     * Recupera tutti i tweet degli utenti seguiti da un utente specifico e li ordina
     * dalla data più recente alla più vecchia.
     * 
     * @param username L'username dell'utente di cui si vogliono vedere i tweet nella timeline.
     * @return Una lista di Tweet ordinati dalla data più recente alla più vecchia.
     */
    
    
    public List<Tweet> getTimelineTweets(String username) {
        List<Utente> utentiSeguiti = utenteService.trovaSeguitiUtente(username);
        List<Tweet> timelineTweets = new ArrayList<>();
        for (Utente utenteSeguito : utentiSeguiti) {
            List<Tweet> tweetUtenteSeguito = tweetRepository.findByUtenteUsername(utenteSeguito.getUsername());
            timelineTweets.addAll(tweetUtenteSeguito);
        }
        timelineTweets.sort(Comparator.comparing(Tweet::getDataOra).reversed());
        return timelineTweets;
    }
    
    
    
	public boolean validaTweet(String content) throws TweetValidationException {
	    if (content == null || content.isEmpty()) {
	        throw new TweetValidationException("Devi scrivere qualcosa per inviare un tweet");
	         
	    }
	    if (content.length() > 140) {
	    	throw new TweetValidationException("Il tweet non può superare i 140 caratteri.");
	    }
	    
	    return true;
	}
	
	
	
    
    
}
