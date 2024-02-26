package com.example.projectTwitter.strategy;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;

public class UserProfileStrategy implements ProfileStrategy {
	
	


	@Override
	public String getViewFollowers(Utente currentUser, Model model, Utente targetUser) {
		 List<Utente> followers = targetUser.getUtentes2();

	        model.addAttribute("utente", targetUser);
	        model.addAttribute("followers", followers);
	        return "followers";
	}
	
	
	@Override
	public String getViewFollowing(Utente currentUser, Model model, Utente targetUser) {
		
		 List<Utente> following = targetUser.getUtentes1();
		 
		    model.addAttribute("utente", targetUser);
		    model.addAttribute("followings", following);
		    return "following";
	}



	
}

