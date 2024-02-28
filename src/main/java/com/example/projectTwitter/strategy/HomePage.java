package com.example.projectTwitter.strategy;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectTwitter.model.Utente;

/**
 * Rappresenta un'interfaccia per la logica di caricamento delle pagine home.
 * Fornisce un metodo per caricare la pagina home per gli user, 
 */
public interface HomePage {
	 /**
     * Carica la pagina home per un dato utente, potenzialmente utilizzando una query di ricerca
     * per filtrare il contenuto e personalizzare i dati mostrati.
     *
     * @param query La stringa di ricerca opzionale utilizzata per filtrare il contenuto.
     * @param model L'oggetto Model utilizzato per aggiungere attributi da visualizzare nella view.
     * @param utente L'oggetto Utente che rappresenta l'utente loggato.
     * @return Il nome della vista che rappresenta la homepage da visualizzare.
     */	
	 String loadHomePage(String query,Model model, Utente utente);
	 
}
