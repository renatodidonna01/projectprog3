package com.example.projectTwitter.strategy;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectTwitter.model.Utente;

public interface ProfileStrategy {
    String getViewFollowers(Utente currentUser, Model model, Utente targetUser);
    
    String getViewFollowing(Utente currentUser, Model model, Utente targetUser);
    
    
}

