package com.example.projectTwitter.controller;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.projectTwitter.exception.TweetValidationException;
import com.example.projectTwitter.model.Hashtag;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.TweetService;
import com.example.projectTwitter.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	private UtenteService utenteService;
	private TweetService tweetService;
	
	 public HomeController(UtenteService utenteService,TweetService tweetService) {
	        this.utenteService = utenteService;
	        this.tweetService=tweetService;
	        
	    }	 
	 
	 
	@GetMapping("/home")
	public String home(@RequestParam(required = false) String query,HttpServletRequest request, Model model) {
		Utente username = utenteService.getUtenteLoggato(request);
		
	    if (username == null) {
	        return "redirect:/login";
	    }
	    
	    // Verifica il ruolo dell'utente,se è admin va alla home
	    boolean isAdmin = utenteService.verificaRuoloUtente(username);
	    if (isAdmin) {
	        return "redirect:/admin/home";
	    }
	
	   
	    // cerca utenti 
	    List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, username.getUsername());
        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
	    
	    	    	  	    
	    //creo lista che mi serve per aggiungere i tweet dei seguiti
	    List<Tweet> timelineTweets = tweetService.getTimelineTweets(username.getUsername());
	   
	    // Recupera tutti gli hashtag
	    List<Hashtag> hashtags = utenteService.trovaTuttiHashtag();
	    
	    	    
	    model.addAttribute("hashtags", hashtags); 
	    model.addAttribute("username", username.getUsername());
	    model.addAttribute("timelineTweets", timelineTweets);
	    return "home";
		
	}
	

	
	@PostMapping("/home")
	public String pubblicaTweet(@RequestParam("content") String content, HttpServletRequest request, @RequestParam("hashtag") Integer hashtagId, Model model, RedirectAttributes redirectAttributes) {
	    Utente utente =  utenteService.getUtenteLoggato(request);
	    String username=utente.getUsername();
	    	    
	    //controllo validità tweet
	    try {
	        tweetService.validaTweet(content); // Valida il tweet
	    } catch (TweetValidationException e) {
	        redirectAttributes.addFlashAttribute("errore", e.getMessage());
	        return "redirect:/home";
	    }
	    
	  //se valido
	  // Recupera l'utente e l'hashtag dal database
	    
	    Hashtag hashtag = utenteService.trovaHashtagPerId(hashtagId); 

	    // Crea e salva il nuovo tweet
	    Tweet tweet = new Tweet();
	    tweet.setTesto(content);
	    tweet.setHashtag(hashtag);
	    tweet.setUtente(utente);
	    tweet.setDataOra(new Timestamp(System.currentTimeMillis()));

	    utenteService.pubblicaTweet(username, tweet);

	    // Imposta un messaggio di successo e reindirizza
	    redirectAttributes.addFlashAttribute("successo", "Tweet pubblicato con successo!");
	    return "redirect:/home";
	}

}
