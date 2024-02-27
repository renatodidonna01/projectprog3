package com.example.projectTwitter.interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.example.projectTwitter.model.Utente;
import com.example.projectTwitter.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * Interceptor che implementa il controllo degli accessi basato sui ruoli degli utenti.
 * Determina se un utente può accedere a specifiche aree del sito in base al suo ruolo.
 */


@Component
public class RoleBasedAccessInterceptor implements HandlerInterceptor {
    private static final String LOGIN_PATH = "/login";
    private static final String ADMIN_PATH = "/admin";
    private static final String LOGOUT_PATH = "/logout";
    private static final String PROFILE_FOLLOWERS_REGEX = "/profilo/.+/followers";
    private static final String PROFILE_FOLLOWING_REGEX = "/profilo/.+/following";

    @Autowired
    private UtenteService utenteService;

    /**
     * Prende decisioni di autorizzazione prima che una richiesta raggiunga il controller.
     * 
     * @param request La richiesta HTTP in arrivo.
     * @param response La risposta HTTP da inviare.
     * @param handler L'oggetto che gestisce la richiesta.
     * @return true se l'utente ha il diritto di accedere alla risorsa richiesta, false altrimenti.
     * @throws Exception in caso di errori.
     */
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

     

        Utente user = utenteService.getUtenteLoggato(request);
        
        if (user == null) {
            response.sendRedirect(LOGIN_PATH);
            return false;
        }

        Utente.Role userRole = user.getRuolo();

        if (Utente.Role.ADMIN.equals(userRole)) {
            if (requestURI.startsWith(ADMIN_PATH) || requestURI.startsWith(LOGOUT_PATH)||   
                 requestURI.matches(PROFILE_FOLLOWERS_REGEX) || requestURI.matches(PROFILE_FOLLOWING_REGEX)) {
                return true;
            } else {
                response.sendRedirect("/access-denied");
                return false;
            }
        }

        if (Utente.Role.USER.equals(userRole)) {
            if (!requestURI.startsWith(ADMIN_PATH)) {
                return true;
            } else {
                response.sendRedirect("/access-denied");
                return false;
            }
        }

        response.sendRedirect("/access-denied");
        return false;
    }
}

