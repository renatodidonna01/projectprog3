package com.example.projectTwitter.strategy;

import java.util.List;

import org.springframework.ui.Model;
import com.example.projectTwitter.model.Utente;


public class AdminProfileStrategy implements ProfileStrategy {


	@Override
	public String getViewFollowers(Utente currentUser, Model model, Utente targetUser) {
		 List<Utente> followers = targetUser.getUtentes2();

	        model.addAttribute("utente", targetUser);
	        model.addAttribute("followers", followers);
	        return "adminFollowers";
	}
	
	
	@Override
	public String getViewFollowing(Utente currentUser, Model model, Utente targetUser) {
		
		 List<Utente> following = targetUser.getUtentes1();
		 
		    model.addAttribute("utente", targetUser);
		    model.addAttribute("followings", following);
		    return "adminFollowing";
	}
	
}