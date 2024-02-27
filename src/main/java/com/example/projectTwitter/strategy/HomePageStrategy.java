package com.example.projectTwitter.strategy;

import org.springframework.ui.Model;

import com.example.projectTwitter.model.Utente;

/**
 * Interfaccia per definire la strategia di caricamento della homepage.
 * 
 * Questa interfaccia fornisce un metodo per caricare la homepage in base al tipo di utente. 
 * L'implementazione specifica determinerà come la homepage viene visualizzata per diversi ruoli utente(user e admin)
 * 
 * @see Model interfaccia di Spring usata per aggiungere attributi al modello
 * @see Utente modello che rappresenta l'utente nel sistema
 */


public interface HomePageStrategy {
	
	 String loadHomePage(Model model, Utente utente);
	 
}
