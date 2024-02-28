package com.example.projectTwitter.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;

/**
 * Controller per gestire il processo di registrazione degli utenti.
 * Fornisce i metodi per mostrare il form di registrazione e per gestire il
 * form con i dati dell'utente.
 */
@Controller
@RequestMapping("/registrazione")
public class RegistrazioneController {

    @Autowired
    private UtenteService utenteService;

    /**
     * Mostra il form di registrazione.
     * 
     * @param model Modello per passare dati alla vista.
     * @return Il nome della vista di registrazione.
     */    
    @GetMapping
    public String mostraFormRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        System.out.println("Chiamato mostraFormRegistrazione");
        return "authentication/registrazione";        
    }
    
    
    /**
     * Gestisce la registrazione di un nuovo utente.
     * L'annotazione @ModelAttribute indica a Spring di mappare automaticamente i campi del form di registrazione
     * inviati nella richiesta POST ai corrispondenti attributi dell'oggetto Utente. Questo processo di "binding" 
     * automatico consente di popolare l'oggetto Utente con i dati inviati dall'utente senza necessità di 
     * estrazione e assegnazione manuale dei valori dei campi.
     * Se la registrazione ha successo, reindirizza alla pagina di login.
     * In caso di errore (es. username o email già in uso), reindirizza nuovamente alla pagina di registrazione
     * mostrando un messaggio di errore.
     * 
     * @param utente L'utente da registrare.
     * @param model Modello per passare dati alla vista.
     * @param redirectAttributes Attributi per passare parametri in caso di reindirizzamento.
     * @return Un reindirizzamento alla vista appropriata a seconda dell'esito della registrazione.
     */    
    @PostMapping
    public String registraUtente(@ModelAttribute Utente utente, Model model,RedirectAttributes redirectAttributes) {
        try {
            utenteService.registraNuovoUtente(utente);
            return "redirect:/login"; // Redirect alla pagina di login dopo la registrazione
        } catch (RuntimeException e) {
        	redirectAttributes.addFlashAttribute("errore", "Il tuo username o la tua email sono già state usate");
        	return "redirect:/registrazione";
        }
    }
    
}





