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



@Controller
public class CercaController {		
		private UtenteService utenteService;
		
		 public CercaController(UtenteService utenteService) {
		        this.utenteService = utenteService;
		    }
		 
		 
				 
		 @GetMapping("/admin/cercatweet")
		 public String cercaTweet(HttpServletRequest request,@RequestParam(required = false)String query,  Model model) {
		    
		     List<Tweet> risultatiRicerca = utenteService.RicercaTweet(query);	
		     
		     Utente utente =  utenteService.getUtenteLoggato(request);
			 
		     
		     model.addAttribute("utente", utente);
		     model.addAttribute("risultatiRicerca", risultatiRicerca);
		     return "cercaTweet";
		 }
		 
		 
}
