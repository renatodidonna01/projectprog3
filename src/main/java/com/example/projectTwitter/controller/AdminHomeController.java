package com.example.projectTwitter.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class AdminHomeController {

	private UtenteService utenteService;
	
	 public AdminHomeController(UtenteService utenteService) {
	        this.utenteService = utenteService;
	    }
	 
	 @GetMapping("/admin/home")
	 public String adminHome(HttpServletRequest request, Model model) {
		 
		 
		 if (request.getSession().getAttribute("username") == null)
		 {
			 return "redirect:/login";
		 }
		
		     
		//creo lista che mi serve per aggiungere gli utenti con piu tweet
		    List<Utente> utentiAttivi = new ArrayList<>();
		    
		    utentiAttivi=utenteService.UtentiAttiviTweet();
		    String username = (String) request.getSession().getAttribute("username");
		    
		    Utente utente = utenteService.trovaUtentePerUsername(username);
			   
		    
		    model.addAttribute("utente", utente);
		    
		    model.addAttribute("utentiAttivi", utentiAttivi);
		    		    
		    return "adminHome"; 		 	 
	 }
	 
	 
	 
	 
	 @GetMapping("/admin/profilo")
		public String AdminvisualizzaProfilo(HttpServletRequest request, Model model) {
		    String username = (String) request.getSession().getAttribute("username");
		    
		    if (username == null) {
		        return "redirect:/login";
		    }
		    
		    Utente utente = utenteService.trovaUtentePerUsername(username);
		    		    
		    model.addAttribute("utente", utente);
	        
		    return "profiloAdmin";
		}   
		
		
		
		
		
		@GetMapping("/admin/profilo/{username}")
		public String AdminmostraProfilo(@PathVariable String username, HttpServletRequest request, Model model) {
		    String currentUsername = (String) request.getSession().getAttribute("username");

		    Utente currentUser = utenteService.trovaUtentePerUsername(currentUsername);
		    Utente targetUser = utenteService.trovaUtentePerUsername(username);

		    if (currentUser == null || targetUser == null) {
		        return "adminHome"; 
		    }

		    //controllo se l'utente corrente segue l'utente di destinazione,controllo la lista dei seguiti e vedo se ci sta targetUser
		    boolean isFollowing = currentUser != null && currentUser.getUtentes1().contains(targetUser);
		    
		    model.addAttribute("isFollowing", isFollowing);
		    model.addAttribute("utente", targetUser);
		    
		    return "adminOspite"; 
		}

		  
	  
}
