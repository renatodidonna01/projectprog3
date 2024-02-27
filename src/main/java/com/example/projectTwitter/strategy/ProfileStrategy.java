package com.example.projectTwitter.strategy;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectTwitter.model.Utente;

/**
 * Interfaccia per definire la strategia di visualizzazione dei followers e following.
 *
 * Questa interfaccia fornisce i metodi per gestire la visualizzazione dei follower e degli utenti seguiti
 * di un dato utente. L'implementazione specifica determinerà come queste informazioni vengono presentate 
 * nell'interfaccia utente.
 *
 * @see Model interfaccia di Spring usata per aggiungere attributi al modello
 * @see Utente modello che rappresenta l'utente nel sistema
 */



public interface ProfileStrategy {
    String getViewFollowers(Utente currentUser, Model model, Utente targetUser);
    
    String getViewFollowing(Utente currentUser, Model model, Utente targetUser);
    
    
}

