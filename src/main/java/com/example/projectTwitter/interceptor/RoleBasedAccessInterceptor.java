
package com.example.projectTwitter.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.model.Utente.Role;
import com.example.projectTwitter.service.CustomAuthenticationService;
import com.example.projectTwitter.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleBasedAccessInterceptor implements HandlerInterceptor {
	  @Autowired
	  private  UtenteService utenteservice;
	
	  @Override
	  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	      
	      // Controlla se la richiesta è per la pagina di login
	      if (request.getRequestURI().equals("/login")) {
	          return true; // Consenti l'accesso alla pagina di login
	      }
	      
	      // Altrimenti, controlla se l'utente è autenticato
	      Utente user = utenteservice.getUtenteLoggato(request); 
	      
	      if (user == null) {
	          response.sendRedirect("/login"); // Reindirizza all'area di login se l'utente non è autenticato
	          return false;
	      }
	      
	      // Altrimenti, l'utente è autenticato e puoi procedere con il controllo dei ruoli
	      Role userRole = user.getRuolo();
	         
	      // Verifica l'accesso in base all'URL della richiesta e al ruolo dell'utente
	      if (request.getRequestURI().startsWith("/admin") && !Role.ADMIN.equals(userRole)) {

	          response.sendRedirect("/access-denied"); 
	          return false;
	      }

	      return true; // Continua con il normale processo di gestione della richiesta
	  }
}


