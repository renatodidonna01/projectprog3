package com.example.projectTwitter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;
//la classe è un controller che gestisce le richieste HTTP
@RestController

//specifica il prefisso dell'url
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    //costruttore
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }
    

    @PostMapping("/salva")
    public Utente salvaUtente(@RequestBody Utente utente) {
        return utenteService.salvaUtente(utente);
    }

    @DeleteMapping("/elimina/{username}")
    public void eliminaUtente(@PathVariable String username) {
        utenteService.eliminaUtentePerUsername(username);
    }

    /*
    //visualizza tutti gli utenti
    @GetMapping("/tutti")
    public List<Utente> getTuttiUtenti() {
        return utenteService.trovaTuttiUtenti();
    }
    
     */
    
    //trova tutti i tweet di un utente
    @GetMapping("/{username}/tweet")
    public List<Tweet> getTweetUtente(@PathVariable String username) {
        return utenteService.trovaTuttiTweetUtente(username);
    }

    @GetMapping("/{username}/seguaci")
    public List<Utente> getSeguaciUtente(@PathVariable String username) {
        return utenteService.trovaSeguaciUtente(username);
    }
    
    
    @GetMapping("/{username}/seguiti")
    public List<Utente> trovaSeguitiUtente(@PathVariable String username) {
        return utenteService.trovaSeguitiUtente(username);
    }
    
    
    @PostMapping("/{username}/tweet")
    public Tweet pubblicaTweet(@PathVariable String username, @RequestBody Tweet tweet) {
        return utenteService.pubblicaTweet(username,tweet);
    }

    
    @PostMapping("/{follower}/segui/{followed}")
    public void seguiUtente(@PathVariable String follower, @PathVariable String followed) {
        utenteService.seguiUtente(follower, followed);
    }
    
    @PostMapping("/{follower}/segui/{defollowed}")
    public void defollowaUtente(@PathVariable String follower, @PathVariable String followed) {
        utenteService.defollowaUtente(follower, followed);
    }
    
}
    
    
    
