package com.example.projectTwitter.strategy;


import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectTwitter.model.Hashtag;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.TweetService;
import com.example.projectTwitter.service.UtenteService;

/**
 * Implementazione dell'interfaccia HomePage.
 * Personalizza la homepage dell'utente con contenuti rilevanti, come i tweet dei seguiti,
 * gli hashtag popolari e i risultati della ricerca di utenti.
 */
public class UserHomePage implements HomePage {
    private TweetService tweetService;
    private UtenteService utenteService;
    
    /**
     * Costruttore che inietta le dipendenze necessarie per il servizio di tweet e utente.
     *
     * @param tweetService Il servizio per accedere ai tweet e alle relative operazioni.
     * @param utenteService Il servizio per accedere agli utenti e alle relative operazioni.
     */
    public UserHomePage(TweetService tweetService, UtenteService utenteService) {
        this.tweetService = tweetService;
        this.utenteService = utenteService;
    }
    
    /**
     * Carica e personalizza la homepage per l'user, includendo i tweet dei seguiti,
     * gli hashtag popolari e i risultati di ricerca degli utenti basati su una query fornita.
     *
     * @param query La stringa di ricerca opzionale utilizzata per filtrare il contenuto.
     * @param model L'oggetto Model utilizzato per aggiungere attributi da visualizzare nella view.
     * @param utente L'oggetto Utente che rappresenta l'utente loggato.
     * @return Il nome della vista "user/home" che rappresenta la homepage  dell'utente.
     */
    @Override
    public String loadHomePage(String query,Model model, Utente utente) {
        // Logica specifica per la home page dell'utente normale
    	
    	
        //creo lista che mi serve per aggiungere i tweet dei seguiti
        List<Tweet> timelineTweets = tweetService.getTimelineTweets(utente.getUsername());
        
        // Recupera tutti gli hashtag
        List<Hashtag> hashtags = utenteService.trovaTuttiHashtag();
        
        
     // cerca utenti 
        List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, utente.getUsername());
        
        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
        model.addAttribute("hashtags", hashtags); 
        model.addAttribute("username", utente.getUsername());
        model.addAttribute("timelineTweets", timelineTweets);
        
        
        return "user/home";
    }
}