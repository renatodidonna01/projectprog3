package com.example.projectTwitter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.CustomAuthenticationService;
import com.example.projectTwitter.service.TweetService;
import com.example.projectTwitter.service.UtenteService;
import com.example.projectTwitter.strategy.AdminHomePageStrategy;
import com.example.projectTwitter.strategy.HomePageStrategy;
import com.example.projectTwitter.strategy.UserHomePageStrategy;

import jakarta.servlet.http.HttpServletRequest;
/**
 * Controller per gestire le funzionalità di login e logout degli utenti.
 * Utilizza CustomAuthenticationService per autenticare gli utenti.
 */


/**
 * Costruisce un LoginController con le dipendenze necessarie per l'autenticazione e la gestione degli utenti e dei tweet.
 * 
 * @param authenticationService Il servizio per l'autenticazione degli utenti.
 * @param utenteService Il servizio per la gestione degli utenti.
 * @param tweetService Il servizio per la gestione dei tweet.
 */

@Controller
public class LoginController {

    private final CustomAuthenticationService authenticationService;
    private final UtenteService utenteService;
    private final TweetService tweetService;
 
    public LoginController(CustomAuthenticationService authenticationService,UtenteService utenteService,TweetService tweetService) {
        this.authenticationService = authenticationService;
        this.utenteService=utenteService;
        this.tweetService=tweetService;
    }
    
    
   
    /**
     * Gestisce il processo di login per gli utenti.
     * Autentica l'utente basandosi su username e password, impostando la sessione in caso di successo.
     * 
     * @param username L' username fornito dall'utente.
     * @param password La password fornita dall'utente.
     * @param request L'oggetto HttpServletRequest utilizzato per impostare la sessione.
     * @param redirectAttributes Gli attributi per il reindirizzamento, utilizzati per inviare messaggi in caso di errore.
     * @param model Il modello MVC per passare dati alla vista.
     * @return Il nome della vista da reindirizzare in caso di successo o fallimento dell'autenticazione.
     */    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,RedirectAttributes redirectAttributes,Model model) {
        if (authenticationService.authenticate(username, password)) {
            request.getSession().setAttribute("username", username); // Memorizza l'username nella sessione 
            Utente utente = utenteService.getUtenteLoggato(request);
            HomePageStrategy strategy;	    
		    
		    
		    boolean isAdmin = utenteService.verificaRuoloUtente(utente);
		    if (isAdmin) {
		    	strategy = new AdminHomePageStrategy();
		    } else {
		        strategy = new UserHomePageStrategy(tweetService, utenteService);
		    }
			   
		    return strategy.loadHomePage(model, utente);		
		}
        
        
        else {
        	 redirectAttributes.addFlashAttribute("errore", "Credenziali errate! Ritenta!");
            return "redirect:/login?error";
        }
    }

    /**
     * Visualizza la pagina di login.
     * 
     * @return Il nome della vista per la pagina di login.
     */
    
    @GetMapping("/login")
    public String showLoginPage() {        
        return "authentication/login";
    }
    
    /**
     * Gestisce le richieste POST per il logout degli utenti.
     * Rimuove l'username dalla sessione e reindirizza alla pagina di login.
     * 
     * @param request L'oggetto HttpServletRequest utilizzato per accedere e modificare la sessione.
     * @return Il percorso di reindirizzamento alla pagina di login.
     */
    
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("username"); // Rimuove l'utente dalla sessione
        return "redirect:/login";
    }

}