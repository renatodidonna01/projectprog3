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
/**
 * Servizio per la gestione dei tweet all'interno della piattaforma.
 * Questo servizio offre funzionalità per la pubblicazione di nuovi tweet, la validazione del contenuto del tweet,
 * e la ricerca di tweet esistenti. Inoltre, fornisce funzionalità per la gestione della timeline degli utenti,
 * visualizzando i tweet degli utenti seguiti.
 */
@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UtenteService utenteService;
    
    /**
     * Costruttore per iniettare le dipendenze richieste nel servizio.
     * 
     * @param tweetRepository Il repository per l'accesso ai tweet.
     * @param utenteService Il servizio per l'accesso agli utenti.
     */    
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
    
    
    /**
     * Valida il contenuto di un tweet, assicurandosi che non sia vuoto e che non superi la lunghezza massima.
     * 
     * @param content Il contenuto del tweet da validare.
     * @return true se il tweet è valido.
     * @throws TweetValidationException se il contenuto del tweet è vuoto o supera la lunghezza massima.
     */
	public boolean validaTweet(String content) throws TweetValidationException {
	    if (content == null || content.isEmpty()) {
	        throw new TweetValidationException("Devi scrivere qualcosa per inviare un tweet");
	         
	    }
	    if (content.length() > 140) {
	    	throw new TweetValidationException("Il tweet non può superare i 140 caratteri.");
	    }
	    
	    return true;
	}
	
	 /**
     * Pubblica un tweet per un utente specifico dopo aver validato il contenuto.
     * 
     * @param username L'username dell'utente che pubblica il tweet.
     * @param tweet L'oggetto Tweet che rappresenta il tweet da pubblicare.
     * @return Il Tweet salvato.
     * @throws TweetValidationException se il contenuto del tweet è vuoto o supera la lunghezza massima.
     */	
	public Tweet pubblicaTweet(String username, Tweet tweet) {
		
		if (tweet.getTesto() == null || tweet.getTesto().isEmpty()) {
	        throw new TweetValidationException("Devi scrivere qualcosa per inviare un tweet");
	    }
	    if (tweet.getTesto().length() > 140) {
	        throw new TweetValidationException("Il tweet non può superare i 140 caratteri.");
	    }
	    
	    
		return tweetRepository.save(tweet);	
	} 
	
	
	 /**
     * Cerca tweet che contengono una determinata stringa di query nel loro testo.
     * 
     * @param query La stringa di ricerca per filtrare i tweet.
     * @return Una lista di {@link Tweet} che corrispondono alla query di ricerca.
     */	
	public List<Tweet> RicercaTweet(String query) {
        return tweetRepository.findByTestoContainingIgnoreCase(query);
    } 
    
	
    
    
}
