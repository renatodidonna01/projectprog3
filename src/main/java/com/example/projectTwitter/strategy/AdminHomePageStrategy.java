package com.example.projectTwitter.strategy;

import org.springframework.ui.Model;

import com.example.projectTwitter.model.Utente;

public class AdminHomePageStrategy implements HomePageStrategy {
    @Override
    public String loadHomePage(Model model, Utente utente, String query) {
        // Logica specifica per la home page dell'amministratore
        return "redirect:/admin/home";
    }
}