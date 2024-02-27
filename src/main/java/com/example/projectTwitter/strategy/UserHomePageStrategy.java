package com.example.projectTwitter.strategy;


import java.util.List;
import org.springframework.ui.Model;
import com.example.projectTwitter.model.Hashtag;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.TweetService;
import com.example.projectTwitter.service.UtenteService;

/**
 * Strategia di caricamento della homepage per gli utenti normali.
 * 
 * Questa classe implementa HomePageStrategy per fornire una logica specifica di visualizzazione
 * della homepage per gli utenti normali. Utilizza TweetService e UtenteService
 * per recuperare i dati necessari alla costruzione della vista, come la timeline dei tweet e la lista di hashtag.
 *
 * @see HomePageStrategy interfaccia per le strategie di caricamento della homepage
 * @see TweetService servizio per la gestione dei tweet
 * @see UtenteService servizio per la gestione degli utenti
 */


public class UserHomePageStrategy implements HomePageStrategy {
    private TweetService tweetService;
    private UtenteService utenteService;

    public UserHomePageStrategy(TweetService tweetService, UtenteService utenteService) {
        this.tweetService = tweetService;
        this.utenteService = utenteService;
    }

    @Override
    public String loadHomePage(Model model, Utente utente) {
        // Logica specifica per la home page dell'utente normale
    	
    	
        //creo lista che mi serve per aggiungere i tweet dei seguiti
        List<Tweet> timelineTweets = tweetService.getTimelineTweets(utente.getUsername());
        
        // Recupera tutti gli hashtag
        List<Hashtag> hashtags = utenteService.trovaTuttiHashtag();

        
        model.addAttribute("hashtags", hashtags); 
        model.addAttribute("username", utente.getUsername());
        model.addAttribute("timelineTweets", timelineTweets);
        return "user/home";
    }
}