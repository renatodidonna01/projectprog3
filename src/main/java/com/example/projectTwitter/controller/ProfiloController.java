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

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class ProfiloController {
	private UtenteService utenteService;
	
	 public ProfiloController(UtenteService utenteService) {
	        this.utenteService = utenteService;
	    }

	@GetMapping("/profilo")
	public String visualizzaProfilo(HttpServletRequest request, Model model) {
	    String username = (String) request.getSession().getAttribute("username");
	    
	    if (username == null) {
	        return "redirect:/login";
	    }
	    
	    Utente utente = utenteService.trovaUtentePerUsername(username);
	    
  
	    model.addAttribute("utente", utente);
        
	    return "profilo";
	}   
	
			
	@GetMapping("/profilo/{username}")
	public String mostraProfilo(@PathVariable String username, HttpServletRequest request, Model model) {
	    String currentUsername = (String) request.getSession().getAttribute("username");
	    Utente currentUser = utenteService.trovaUtentePerUsername(currentUsername);
	    Utente targetUser = utenteService.trovaUtentePerUsername(username);

	    if (currentUser == null) {
	        throw new UtenteNotFoundException("Utente corrente non trovato.");
	    }
	    if (targetUser == null) {
	        throw new UtenteNotFoundException("Utente di destinazione non trovato.");
	    }

	    boolean isFollowing = currentUser.getUtentes1().contains(targetUser);
	    model.addAttribute("isFollowing", isFollowing);
	    model.addAttribute("utente", targetUser);

	    return "profiloOspite";
	}
	  
	

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

	
	
	@GetMapping("/profilo/{username}/following")
	public String mostraFollowing(HttpServletRequest request,@PathVariable String username, Model model) {
		String currentUsername = (String) request.getSession().getAttribute("username");
		Utente currentUser=utenteService.trovaUtentePerUsername(currentUsername);
		
	    Utente utente = utenteService.trovaUtentePerUsername(username);
	    if (utente == null) {
	        throw new UtenteNotFoundException("Utente non trovato.");
	    }

	    List<Utente> following = utente.getUtentes1();
	    
	    
	    model.addAttribute("utente", utente);
	    model.addAttribute("followings", following);

	    
	    // Verifica il ruolo dell'utente corrente
	    if (Utente.Role.ADMIN.equals(currentUser.getRuolo())) {
	        return "adminFollowing"; // Pagina per l'admin
	        
	    } else {
	        return "following"; // Pagina per l'utente normale
	    }
	    
	}

	
	@GetMapping("/profilo/{username}/followers")
	public String mostraFollowers(HttpServletRequest request,@PathVariable String username, Model model) {
		String currentUsername = (String) request.getSession().getAttribute("username");
		Utente currentUser=utenteService.trovaUtentePerUsername(currentUsername);
	    Utente utente = utenteService.trovaUtentePerUsername(username);
	    if (utente == null) {
	        throw new UtenteNotFoundException("Utente non trovato.");
	    }

	    List<Utente> followers = utente.getUtentes2();
	    model.addAttribute("utente", utente);
	    model.addAttribute("followers", followers);
	    
	    
	    // Verifica il ruolo dell'utente corrente
	    if (Utente.Role.ADMIN.equals(currentUser.getRuolo())) {
	        return "adminFollowers"; // Pagina per l'admin
	    } else {
	        return "followers"; // Pagina per l'utente normale
	    }

	    
	}

   }
