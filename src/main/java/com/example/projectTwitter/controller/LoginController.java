package com.example.projectTwitter.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projectTwitter.service.CustomAuthenticationService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final CustomAuthenticationService authenticationService;
 
    public LoginController(CustomAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        if (authenticationService.authenticate(username, password)) {
            request.getSession().setAttribute("username", username); // Memorizza l'username nella sessione
            return "redirect:/home";
        } else {
        	 redirectAttributes.addFlashAttribute("errore", "Credenziali errate! Ritenta!");
            return "redirect:/login?error";
        }
    }
    
    @GetMapping("/login")
    public String showLoginPage() {        
        return "login";
    }
    
    
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("username"); // Rimuove l'utente dalla sessione
        return "redirect:/login";
    }

}
