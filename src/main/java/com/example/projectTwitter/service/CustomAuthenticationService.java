package com.example.projectTwitter.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.repository.UtenteRepository;

import jakarta.servlet.http.HttpServletRequest;

//gestione autenticazione utenti


@Service
public class CustomAuthenticationService {

    private final UtenteRepository userRepository;
    private final PasswordEncoder passwordEncoder;

   
    public CustomAuthenticationService(UtenteRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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


