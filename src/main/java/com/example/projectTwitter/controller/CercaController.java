package com.example.projectTwitter.controller;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller per la gestione della ricerca di tweet all'interno della piattaforma Twitter.
 * Permette agli utenti amministratori di cercare tweet degli utenti.
 */

@Controller
public class CercaController {		
		private UtenteService utenteService;
		
		 public CercaController(UtenteService utenteService) {
		        this.utenteService = utenteService;
		    }
		 
 /**
     * Effettua la ricerca di tweet basata sulla query fornita.
     * 
     * @param request L'oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param query La stringa di ricerca fornita dall'utente. È opzionale.
     * @param model L'oggetto Model per passare dati alla vista.
	 * @return Il nome della vista da visualizzare con i risultati della ricerca.
 */
		 
				 
		 @GetMapping("/admin/cercatweet")
		 public String cercaTweet(HttpServletRequest request,@RequestParam(required = false)String query,  Model model) {
		    
		     List<Tweet> risultatiRicerca = utenteService.RicercaTweet(query);	
		     
		     Utente utente =  utenteService.getUtenteLoggato(request);
			 
		     
		     model.addAttribute("utente", utente);
		     model.addAttribute("risultatiRicerca", risultatiRicerca);
		     return "admin/cercaTweet";
		 }
		 
		 
}
