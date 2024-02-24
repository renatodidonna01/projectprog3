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


@Controller
@RequestMapping("/registrazione")
public class RegistrazioneController {

    @Autowired
    private UtenteService utenteService;

   
    @GetMapping
    public String mostraFormRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        System.out.println("Chiamato mostraFormRegistrazione");
        return "registrazione";        
    }
    
    

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





