package com.example.projectTwitter.strategy;

import org.springframework.ui.Model;

import com.example.projectTwitter.model.Utente;

public interface HomePageStrategy {
	
	 String loadHomePage(Model model, Utente utente, String query);
	 
}
