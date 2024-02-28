package com.example.projectTwitter.controller;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller per la gestione delle funzionalità amministrative della piattaforma Twitter.
 * Fornisce i metodi per visualizzare la home dell'admin, gestire i profili e visualizzare i profili degli utenti.
 */
@Controller
public class AdminHomeController {

	private UtenteService utenteService;
	
	 public AdminHomeController(UtenteService utenteService) {
	        this.utenteService = utenteService;
	    }
	 
	 
	 /**
	     * Visualizza la homepage dell'admin mostrando gli utenti più attivi basati sui tweet.
	     * 
	     * @param request L'oggetto HttpServletRequest per ottenere informazioni sulla richiesta.
	     * @param model L'oggetto Model per passare attributi alla vista.
	     * @return Il nome della vista da visualizzare.
	     */	 
	 @GetMapping("/admin/home")
	 public String adminHome(HttpServletRequest request, Model model) {
		 
		 Utente utente = utenteService.getUtenteLoggato(request);
		 
		 if (utente == null)
		 {
			 return "redirect:/login";
		 }
				     
		//creo lista che mi serve per aggiungere gli utenti con piu tweet
		 List<Utente> utentiAttivi = utenteService.utentiAttiviTweet();
		 			    		    		    
		    model.addAttribute("utente", utente);
		    model.addAttribute("utentiAttivi", utentiAttivi);
		    
		    return "admin/adminHome"; 		 	 
	 }
	 
	 
	 /**
	     * Visualizza il profilo dell'amministratore corrente.
	     * 
	     * @param request L'oggetto HttpServletRequest per ottenere informazioni sulla richiesta.
	     * @param model L'oggetto Model per passare attributi alla vista.
	     * @return Il nome della vista da visualizzare.
	     */	 
	 @GetMapping("/admin/profilo")
		public String AdminvisualizzaProfilo(HttpServletRequest request, Model model) {
		 
		 Utente utente = utenteService.getUtenteLoggato(request);
		 String username=utente.getUsername();
		    
		    if (username == null) {
		        return "redirect:/login";
		    }
		    		    		    
		    model.addAttribute("utente", utente);
	        
		    return "admin/profiloAdmin";
		}   
		
	 /**
	     * Visualizza il profilo di un altro utente specificato dall'username.
	     * 
	     * @param username Lo username dell'utente di cui visualizzare il profilo.
	     * @param request L'oggetto HttpServletRequest per ottenere informazioni sulla richiesta.
	     * @param model L'oggetto Model per passare attributi alla vista.
	     * @return Il nome della vista da visualizzare.
	     */		
		@GetMapping("/admin/profilo/{username}")
		public String AdminmostraProfilo(@PathVariable String username, HttpServletRequest request, Model model) {
		    

		    Utente currentUser = utenteService.getUtenteLoggato(request);
		    
		    //String currentUsername = currentUser.getUsername();
		    Utente targetUser = utenteService.trovaUtentePerUsername(username);

		    if (currentUser == null || targetUser == null) {
		        return "admin/adminHome"; 
		    }

		    //controllo se l'utente corrente segue l'utente di destinazione,controllo la lista dei seguiti e vedo se ci sta targetUser
		    boolean isFollowing = currentUser != null && currentUser.getUtentes1().contains(targetUser);
		    
		    model.addAttribute("isFollowing", isFollowing);
		    model.addAttribute("utente", targetUser);
		    
		    return "admin/adminOspite"; 
		}

		  	  
}
