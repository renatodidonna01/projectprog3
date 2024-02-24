package com.example.projectTwitter.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projectTwitter.model.Hashtag;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	
	private UtenteService utenteService;
	
	 public HomeController(UtenteService utenteService) {
	        this.utenteService = utenteService;
	    }
	 
	 
	 
	@GetMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		 if (request.getSession().getAttribute("username") == null)
		 {
			 return "redirect:/login";
		 }
	    String username = (String) request.getSession().getAttribute("username");
	    
	    Utente utente = utenteService.trovaUtentePerUsername(username);
	    
	    // Verifica il ruolo dell'utente
	    if (Utente.Role.ADMIN.equals(utente.getRuolo())) {
	        return "redirect:/admin/home"; // Reindirizza alla home dell'admin se l'utente è un admin
	    }
	    
	    	    
	    System.out.println("Username from session: " + username); // Aggiungi questo log
	    
	    //recupero gli utenti seguiti
	    List<Utente> utentiSeguiti = utenteService.trovaSeguitiUtente(username);
	    
	    //creo lista che mi serve per aggiungere i tweet dei seguiti
	    List<Tweet> timelineTweets = new ArrayList<>();

	    
	    //aggiungo i tweet delle persone che segue l'utente 
	    for(Utente utenteSeguito:utentiSeguiti)
	    {
	    	List<Tweet> tweetUtenteSeguito =utenteService.trovaTuttiTweetUtente(utenteSeguito.getUsername());
	    	timelineTweets.addAll(tweetUtenteSeguito);
	    }
	    
	    //ordino i tweet dalla data più recente
	    Collections.sort(timelineTweets, Comparator.comparing(Tweet::getDataOra,Collections.reverseOrder()));
	    
	    
	    	    
	    // Recupera tutti gli hashtag
	    List<Hashtag> hashtags = utenteService.trovaTuttiHashtag();
	    
	    // Altri codici e logica necessari
	    
	    model.addAttribute("hashtags", hashtags); 
	    model.addAttribute("username", username);
	    model.addAttribute("timelineTweets", timelineTweets);
	    return "home";
	}
	
	
	

	
	@PostMapping("/home")
	public String pubblicaTweet(@RequestParam("content") String content, HttpServletRequest request, @RequestParam("hashtag") Integer hashtagId, Model model, RedirectAttributes redirectAttributes) {
	    String username = (String) request.getSession().getAttribute("username");

	    // Verifica che il contenuto non sia vuoto
	    if (content == null || content.isEmpty()) {
	        redirectAttributes.addFlashAttribute("errore", "Devi scrivere qualcosa per inviare un tweet");
	        return "redirect:/home";
	    }

	    // Verifica la lunghezza del tweet
	    if (content.length() > 140) {
	        redirectAttributes.addFlashAttribute("errore", "Il tweet non può superare i 140 caratteri.");
	        return "redirect:/home";
	    }

	    // Recupera l'utente e l'hashtag dal database
	    Utente utente = utenteService.trovaUtentePerUsername(username);
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
