package com.example.projectTwitter.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.projectTwitter.model.Tweet;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.repository.HashtagRepository;
import com.example.projectTwitter.repository.TweetRepository;
import com.example.projectTwitter.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EsploraController {

	
	private HashtagRepository hashtagRepository;
	private UtenteService utenteService;
	private TweetRepository tweetRepository;
	
	 public EsploraController(HashtagRepository hashtagRepository,UtenteService utenteService,TweetRepository tweetRepository) {
	        this.hashtagRepository = hashtagRepository;
	        this.utenteService = utenteService;
	        this.tweetRepository=tweetRepository;
	    }
	
	 @GetMapping("/admin/esplora")
	    public String mostraCategorie(HttpServletRequest request,Model model) {
	          
		 List <String> categorie= hashtagRepository.trovaCategorie();
		 String username = (String) request.getSession().getAttribute("username");
		 Utente utente = utenteService.trovaUtentePerUsername(username);
		 		 
		 model.addAttribute("utente", utente);
		 model.addAttribute("categorie", categorie);
		 
	      return "esplora"; 	 
	    }
	 
	 
	 
	 
	 @GetMapping("/admin/categoria/{categoria}")
	 public String mostraTweetPerCategoria(@PathVariable("categoria") String categoria, Model model,HttpServletRequest request) {
	     List<Tweet> tweetPerCategoria = tweetRepository.findByHashtagTesto(categoria);
	     String username = (String) request.getSession().getAttribute("username");
         Utente utente = utenteService.trovaUtentePerUsername(username);
		 
		 
         model.addAttribute("categoriaNome", categoria);
		 model.addAttribute("utente", utente);
	     model.addAttribute("tweet", tweetPerCategoria);
	     return "tweetPerCategoria"; 
	 }

	
	 
	 
	 
	 
	 
	 
	 
	
	 
		 
}
