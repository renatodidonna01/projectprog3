package com.example.projectTwitter.strategy;

import java.util.List;

import org.springframework.ui.Model;
import com.example.projectTwitter.model.Utente;

/**
 * Implementazione della strategia per la visualizzazione dei profili utente per gli amministratori.
 * 
 * Questa classe implementa ProfileStrategy per definire un comportamento specifico nella
 * visualizzazione dei follower e degli utenti seguiti (following) per un utente con ruolo di amministratore.
 
 * 
 * @see ProfileStrategy interfaccia per le strategie di visualizzazione del profilo
 * @see Utente modello che rappresenta l'utente nel sistema
 * @see Model interfaccia di Spring usata per aggiungere attributi al modello
 */
public class AdminProfileStrategy implements ProfileStrategy {


	@Override
	public String getViewFollowers(Utente currentUser, Model model, Utente targetUser) {
		 List<Utente> followers = targetUser.getUtentes2();

	        model.addAttribute("utente", targetUser);
	        model.addAttribute("followers", followers);
	        return "admin/adminFollowers";
	}
	
	
	@Override
	public String getViewFollowing(Utente currentUser, Model model, Utente targetUser) {
		
		 List<Utente> following = targetUser.getUtentes1();
		 
		    model.addAttribute("utente", targetUser);
		    model.addAttribute("followings", following);
		    return "admin/adminFollowing";
	}
	
}