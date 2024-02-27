package com.example.projectTwitter.strategy;

import org.springframework.ui.Model;

import com.example.projectTwitter.model.Utente;

/**
 * Implementazione della strategia per il caricamento della homepage per gli utenti amministratori.
 * 
 * Questa classe fa parte di una strategia di design pattern utilizzata per personalizzare
 * il comportamento della homepage in base al ruolo dell'utente. In questo caso, quando un utente
 * con ruolo di amministratore accede alla piattaforma, viene reindirizzato alla homepage specifica
 * per gli amministratori.
 * 
 * @see HomePageStrategy interfaccia per le strategie di caricamento della homepage
 * @see Utente modello che rappresenta l'utente nel sistema
 * @see Model interfaccia di Spring usata per aggiungere attributi al modello
 */

public class AdminHomePageStrategy implements HomePageStrategy {
    @Override
    public String loadHomePage(Model model, Utente utente) {
        
        return "redirect:/admin/home";
    }
}