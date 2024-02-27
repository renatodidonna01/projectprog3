package com.example.projectTwitter.strategy;


import java.util.List;
import org.springframework.ui.Model;
import com.example.projectTwitter.model.Hashtag;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.TweetService;
import com.example.projectTwitter.service.UtenteService;


public class UserHomePageStrategy implements HomePageStrategy {
    private TweetService tweetService;
    private UtenteService utenteService;

    public UserHomePageStrategy(TweetService tweetService, UtenteService utenteService) {
        this.tweetService = tweetService;
        this.utenteService = utenteService;
    }

    @Override
    public String loadHomePage(Model model, Utente utente, String query) {
        // Logica specifica per la home page dell'utente normale
    	
    	// cerca utenti 
        List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, utente.getUsername());
        
        //creo lista che mi serve per aggiungere i tweet dei seguiti
        List<Tweet> timelineTweets = tweetService.getTimelineTweets(utente.getUsername());
        
        // Recupera tutti gli hashtag
        List<Hashtag> hashtags = utenteService.trovaTuttiHashtag();

        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
        model.addAttribute("hashtags", hashtags); 
        model.addAttribute("username", utente.getUsername());
        model.addAttribute("timelineTweets", timelineTweets);
        return "home";
    }
}