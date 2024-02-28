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


/**
 * Controller per esplorare le diverse categorie di tweet e visualizzare tweet per specifiche categorie.
 */
@Controller
public class EsploraController {
	 /**
     * Costruisce un EsploraController con le dipendenze necessarie.
     * 
     * @param hashtagRepository Il repository per accedere alle informazioni sugli hashtag.
     * @param utenteService Il servizio per gestire gli utenti.
     * @param tweetRepository Il repository per accedere ai tweet.
     */	
	private HashtagRepository hashtagRepository;
	private UtenteService utenteService;
	private TweetRepository tweetRepository;
	
	 public EsploraController(HashtagRepository hashtagRepository,UtenteService utenteService,TweetRepository tweetRepository) {
	        this.hashtagRepository = hashtagRepository;
	        this.utenteService = utenteService;
	        this.tweetRepository=tweetRepository;
	    }
	 /**
	     * Mostra le categorie disponibili per esplorare i tweet.
	     * 
	     * @param request L'oggetto HttpServletRequest per ottenere informazioni sulla sessione corrente.
	     * @param model L'oggetto Model per trasferire dati alla vista.
	     * @return Il nome della vista da mostrare.
	     */	 
	 @GetMapping("/admin/esplora")
	    public String mostraCategorie(HttpServletRequest request,Model model) {
	          
		 List <String> categorie= hashtagRepository.trovaCategorie();
		 String username = (String) request.getSession().getAttribute("username");
		 Utente utente = utenteService.trovaUtentePerUsername(username);
		 		 
		 model.addAttribute("utente", utente);
		 model.addAttribute("categorie", categorie);
		 
	      return "admin/esplora"; 	 
	    }
	 
	 /**
	     * Mostra i tweet relativi a una specifica categoria.
	     * 
	     * @param categoria La categoria di cui visualizzare i tweet.
	     * @param model L'oggetto Model per trasferire dati alla vista.
	     * @param request L'oggetto HttpServletRequest per ottenere informazioni sulla sessione corrente.
	     * @return Il nome della vista da mostrare con i tweet della categoria specificata.
	     */
	 @GetMapping("/admin/categoria/{categoria}")
	 public String mostraTweetPerCategoria(@PathVariable("categoria") String categoria, Model model,HttpServletRequest request) {
	     List<Tweet> tweetPerCategoria = tweetRepository.findByHashtagTesto(categoria);
	     String username = (String) request.getSession().getAttribute("username");
         Utente utente = utenteService.trovaUtentePerUsername(username);
		 
		 
         model.addAttribute("categoriaNome", categoria);
		 model.addAttribute("utente", utente);
	     model.addAttribute("tweet", tweetPerCategoria);
	     return "admin/tweetPerCategoria"; 
	 }

	
	 
	 
	 
	 
	 
	 
	 
	
	 
		 
}
