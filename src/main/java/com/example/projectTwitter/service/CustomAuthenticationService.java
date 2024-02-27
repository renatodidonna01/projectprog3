package com.example.projectTwitter.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.repository.UtenteRepository;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Servizio per l'autenticazione degli utenti.
 * Fornisce metodi per autenticare gli utenti, verificare se un utente è loggato e ottenere l'username dell'utente corrente.
 */

@Service
public class CustomAuthenticationService {

    private final UtenteRepository userRepository;
    private final PasswordEncoder passwordEncoder;

   
    public CustomAuthenticationService(UtenteRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * Autentica un utente basandosi su username e password.
     * 
     * @param username L'username dell'utente da autenticare.
     * @param password La password dell'utente.
     * @return true se l'utente esiste e la password è corretta, false altrimenti.
     */
    
    public boolean authenticate(String username, String password) {
        Utente user = userRepository.findByUsername(username);
// Verifica se l'utente esiste e la password è corretta,confrontando la password fornita con quella hashata nel database 
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
    
    
    public boolean isUserLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute("username") != null;
    }

    public String getCurrentUsername(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("username");
    }
    
 
    
}


