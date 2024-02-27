package com.example.projectTwitter.strategy;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;

/**
 * Strategia di visualizzazione del profilo utente per gestire le visualizzazioni di follower e seguiti.
 * 
 * Implementa ProfileStrategy per fornire una logica specifica per la visualizzazione
 * delle pagine dei follower e dei seguiti di un utente all'interno della piattaforma.
 *
 * @see ProfileStrategy interfaccia per le strategie di visualizzazione del profilo
 */


public class UserProfileStrategy implements ProfileStrategy {
	
	
	@Override
	public String getViewFollowers(Utente currentUser, Model model, Utente targetUser) {
		 List<Utente> followers = targetUser.getUtentes2();

	        model.addAttribute("utente", targetUser);
	        model.addAttribute("followers", followers);
	        return "user/followers";
	}
	
	
	@Override
	public String getViewFollowing(Utente currentUser, Model model, Utente targetUser) {
		
		 List<Utente> following = targetUser.getUtentes1();
		 
		    model.addAttribute("utente", targetUser);
		    model.addAttribute("followings", following);
		    return "user/following";
	}



	
}

