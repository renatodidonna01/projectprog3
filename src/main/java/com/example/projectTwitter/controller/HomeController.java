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
import com.example.projectTwitter.strategy.AdminHomePageStrategy;
import com.example.projectTwitter.strategy.HomePageStrategy;
import com.example.projectTwitter.strategy.UserHomePageStrategy;


import jakarta.servlet.http.HttpServletRequest;


/**
 * Controller per la gestione della homepage e della pubblicazione dei tweet.
 */

/**
 * Costruisce HomeController con le dipendenze necessarie.
 * 
 * @param utenteService Il servizio per gestire le operazioni relative agli utenti.
 * @param tweetService Il servizio per gestire le operazioni relative ai tweet.
 */


@Controller
public class HomeController {
	private UtenteService utenteService;
	private TweetService tweetService;
	
	 public HomeController(UtenteService utenteService,TweetService tweetService) {
	        this.utenteService = utenteService;
	        this.tweetService=tweetService;
	        
	    }	 
	 
	  /**
	     * Gestisce le richieste GET alla homepage, mostrando contenuti personalizzati basati sulla query di ricerca, se presente.
	     * 
	     * @param query La query di ricerca per trovare utenti specifici (opzionale).
	     * @param request La richiesta HTTP corrente.
	     * @param model Il modello per passare dati alla vista.
	     * @return Il nome della vista da renderizzare.
	     */	
	 
	 
		@GetMapping("/home")
		public String home(@RequestParam(required = false) String query,HttpServletRequest request, Model model) {
			Utente utente = utenteService.getUtenteLoggato(request);
			String username = (String) request.getSession().getAttribute("username");
		    if (utente == null) {
		        return "redirect:/login";
		    }	    
		    
			// cerca utenti 
	        List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, utente.getUsername());
	        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
	        model.addAttribute("username", username);
	        
	        return "user/home";
		}
	
		 /**
	     * Gestisce le richieste POST per la pubblicazione di nuovi tweet.
	     * Valida il contenuto del tweet e lo associa all'utente e all'hashtag specificati.
	     * 
	     * @param content Il contenuto del tweet.
	     * @param request La richiesta HTTP corrente.
	     * @param hashtagId L'ID dell'hashtag associato al tweet.
	     * @param model Il modello per passare dati alla vista.
	     * @param redirectAttributes Attributi per passare messaggi o dati in caso di reindirizzamento.
	     * @return Il percorso di reindirizzamento dopo l'elaborazione.
	     */		 
	
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
	  // Recupera  l'hashtag dal database
	    
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
