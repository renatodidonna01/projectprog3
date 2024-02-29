package com.example.projectTwitter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.CustomAuthenticationService;
import com.example.projectTwitter.service.UtenteService;


import jakarta.servlet.http.HttpServletRequest;
/**
 * Controller per gestire le funzionalità di login e logout degli utenti.
 * Utilizza CustomAuthenticationService per autenticare gli utenti.
 */


/**
 * Costruisce un LoginController con le dipendenze necessarie per l'autenticazione.
 * 
 * @param authenticationService Il servizio per l'autenticazione degli utenti.
 * @param utenteService Il servizio per la gestione degli utenti.
 * @param tweetService Il servizio per la gestione dei tweet.
 */
@Controller
public class LoginController {

    private final CustomAuthenticationService authenticationService;
    private final UtenteService utenteService;
  
 
    public LoginController(CustomAuthenticationService authenticationService,UtenteService utenteService) {
        this.authenticationService = authenticationService;
        this.utenteService=utenteService;
        
    }
    
    
   
    /**
     * Gestisce il processo di login per l'applicazione.
     * Autentica l'utente basandosi su username e password. Se l'autenticazione ha successo,
     * l'username dell'utente viene memorizzato nella sessione, e viene effettuato un reindirizzamento
     * alla pagina home appropriata in base al ruolo dell'utente. Gli utenti con ruolo di amministratore
     * vengono reindirizzati a "/admin/home", mentre tutti gli altri utenti a "/home".
     * In caso di fallimento dell'autenticazione, viene reindirizzato nuovamente alla pagina di login
     * con un messaggio di errore.
     *
     * @param username Lo username fornito dall'utente.
     * @param password La password fornita dall'utente.
     * @param request L'oggetto HttpServletRequest utilizzato per gestire la sessione.
     * @param redirectAttributes Utilizzato per passare attributi in caso di reindirizzamento,
     *        in questo caso per mostrare un messaggio di errore se l'autenticazione fallisce.
     * @return Una stringa che indica la vista o il percorso di reindirizzamento. Questo può essere
     *         il percorso verso la homepage dell'amministratore, la homepage dell'utente o la pagina di login
     *         con un parametro di errore se l'autenticazione fallisce.
     */   
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        if (authenticationService.authenticate(username, password)) {
            request.getSession().setAttribute("username", username); // Memorizza l'username nella sessione 
            Utente utente = utenteService.getUtenteLoggato(request);
            
            boolean isAdmin = utenteService.verificaRuoloUtente(utente);
		    if (isAdmin) {
		    	return "redirect:/admin/home"; 
		    } else {
		    	return "redirect:/home"; 
		    }
            		
		}       
        else {
        	 redirectAttributes.addFlashAttribute("errore", "Credenziali errate! Ritenta!");
            return "redirect:/login?error";
        }
    }

    /**
     * Mostra la pagina di login.
     * Questo metodo gestisce la richiesta GET per la pagina di login, restituendo la vista
     * corrispondente alla pagina di autenticazione dove gli utenti possono inserire le loro credenziali.
     * 
     * @return Il nome della vista per la pagina di login. La vista è definita in "authentication/login",
     *         che corrisponde al percorso del file della vista all'interno della cartella delle risorse del template.
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