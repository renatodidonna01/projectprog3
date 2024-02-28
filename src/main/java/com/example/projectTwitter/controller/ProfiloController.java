package com.example.projectTwitter.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectTwitter.exception.UtenteNotFoundException;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;
import com.example.projectTwitter.strategy.AdminProfileStrategy;
import com.example.projectTwitter.strategy.ProfileStrategy;
import com.example.projectTwitter.strategy.UserProfileStrategy;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class ProfiloController {
	private UtenteService utenteService;
	
	 public ProfiloController(UtenteService utenteService) {
	        this.utenteService = utenteService;
	    }
	 
	 /**
	  * Gestisce la visualizzazione della pagina del profilo dell'utente loggato.
	  * Se l'utente non è loggato, reindirizza alla pagina di login. Supporta la ricerca di altri utenti
	  * basata su una query fornita.
	  * 
	  * @param query La query di ricerca per trovare altri utenti (opzionale).
	  * @param request L'oggetto HttpServletRequest per accedere alla sessione corrente.
	  * @param model Il modello per passare dati alla vista.
	  * @return Il nome della vista del profilo da visualizzare.
	  */	 
	@GetMapping("/profilo")
	public String visualizzaProfilo(@RequestParam(required = false) String query,HttpServletRequest request, Model model) {
		Utente utente =  utenteService.getUtenteLoggato(request);
	    String username=utente.getUsername();
	    
	    if (username == null) {
	        return "redirect:/login";
	    }
	    
	     // cerca utenti 
        List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, utente.getUsername());
        
        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
	    model.addAttribute("utente", utente);
        
	    return "user/profilo";
	}   
	
	/**
	 * Mostra il profilo di un altro utente specificato dal suo username. Verifica se 
	 * l'utente corrente lo segue o meno.
	 * 
	 * @param query La query di ricerca per trovare altri utenti (opzionale).
	 * @param username Lo username dell'utente di cui visualizzare il profilo.
	 * @param request L'oggetto HttpServletRequest per accedere alla sessione corrente.
	 * @param model Il modello per passare dati alla vista.
	 * @return Il nome della vista del profilo ospite da visualizzare.
	 */	
	@GetMapping("/profilo/{username}")
	public String mostraProfilo(@RequestParam(required = false) String query,@PathVariable String username, HttpServletRequest request, Model model) {
	    
	    Utente currentUser = utenteService.getUtenteLoggato(request);
	    
	    Utente targetUser = utenteService.trovaUtentePerUsername(username);

	    if (currentUser == null) {
	        throw new UtenteNotFoundException("Utente corrente non trovato.");
	    }
	    
	    if (targetUser == null) {
	        throw new UtenteNotFoundException("Utente di destinazione non trovato.");
	    }
             
	    boolean isFollowing = currentUser.getUtentes1().contains(targetUser);
	    
	     // cerca utenti 
        List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, currentUser.getUsername());
        
        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
        
	    model.addAttribute("isFollowing", isFollowing);
	    model.addAttribute("utente", targetUser);
	    model.addAttribute("username", currentUser.getUsername());

	    return "user/profiloOspite";
	}
	  
	/**
	 * Gestisce l'azione di seguire o smettere di seguire un altro utente.
	 * 
	 * @param username Lo username dell'utente da seguire o smettere di seguire.
	 * @param request L'oggetto HttpServletRequest per accedere alla sessione corrente e ottenere lo username dell'utente loggato.
	 * @return Un reindirizzamento alla pagina del profilo dell'utente specificato.
	 */	
	@PostMapping("/followUnfollow")
	public String followUnfollow(@RequestParam String username, HttpServletRequest request) {
		
	    String currentUsername = (String) request.getSession().getAttribute("username");
	    Utente currentUser = utenteService.trovaUtentePerUsername(currentUsername);
	    Utente targetUser = utenteService.trovaUtentePerUsername(username);

	    if (currentUser == null || targetUser == null) {
	        throw new UtenteNotFoundException("Utente non trovato.");
	    }

	    if (currentUser.getUtentes1().contains(targetUser)) {
	        utenteService.defollowaUtente(currentUsername, username);
	    } else {
	        utenteService.seguiUtente(currentUsername, username);
	    }

	    return "redirect:/profilo/" + username;
	}

	/**
	 * Mostra gli utenti che un dato utente sta seguendo.
	 * 
	 * @param query La query di ricerca per filtrare gli utenti seguiti (opzionale).
	 * @param request L'oggetto HttpServletRequest per accedere alla sessione corrente.
	 * @param username L' username dell'utente di cui si vogliono vedere gli utenti seguiti.
	 * @param model Il modello per passare dati alla vista.
	 * @return Il nome della vista che mostra gli utenti seguiti.
	 */		
	@GetMapping("/profilo/{username}/following")
	public String mostraFollowing(@RequestParam(required = false) String query,HttpServletRequest request,@PathVariable String username, Model model) {
		String currentUsername = (String) request.getSession().getAttribute("username");
		Utente currentUser=utenteService.trovaUtentePerUsername(currentUsername);
		
	    Utente utente = utenteService.trovaUtentePerUsername(username);
	    if (utente == null) {
	        throw new UtenteNotFoundException("Utente non trovato.");
	    }

          ProfileStrategy strategy;
	    
	    // Verifica il ruolo dell'utente corrente
	    if (utenteService.verificaRuoloUtente(currentUser)) {
	        strategy=new AdminProfileStrategy();
	        model.addAttribute("username", currentUsername);
	        
	        
	    } else {
	    	strategy=new UserProfileStrategy();
	    	// cerca utenti 
	        List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, currentUser.getUsername());
	        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
	        model.addAttribute("username", currentUsername);
	    }	   
	    return strategy.getViewFollowing(currentUser, model, utente);
	    	    
	}

	/**
	 * Mostra gli utenti che seguono un dato utente.
	 * 
	 * @param query La query di ricerca per filtrare i followers (opzionale).
	 * @param request L'oggetto HttpServletRequest per accedere alla sessione corrente.
	 * @param username L'username dell'utente di cui si vogliono vedere i followers.
	 * @param model Il modello per passare dati alla vista.
	 * @return Il nome della vista che mostra i followers.
	 */	
	@GetMapping("/profilo/{username}/followers")
	public String mostraFollowers(@RequestParam(required = false) String query,HttpServletRequest request,@PathVariable String username, Model model) {
		String currentUsername = (String) request.getSession().getAttribute("username");
		Utente currentUser=utenteService.trovaUtentePerUsername(currentUsername);
	    Utente utente = utenteService.trovaUtentePerUsername(username);
	    if (utente == null) {
	        throw new UtenteNotFoundException("Utente non trovato.");
	    }
          ProfileStrategy strategy;
	    
	    // Verifica il ruolo dell'utente corrente
	    if (utenteService.verificaRuoloUtente(currentUser)) {
	        strategy=new AdminProfileStrategy();
	        model.addAttribute("username", currentUsername);
	        	        
	    } else {
	    	strategy=new UserProfileStrategy();
	    	// cerca utenti 
	        List<Utente> risultatiRicercaUtenti = utenteService.cercaUtentiConQuery(query, currentUser.getUsername());
	        model.addAttribute("risultatiRicercaUtenti", risultatiRicercaUtenti);
	        model.addAttribute("username", currentUsername);
	    }	   
	    return strategy.getViewFollowers(currentUser, model, utente);	    
	}

   }
