package com.example.projectTwitter.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe di configurazione per la registrazione degli interceptors nell'applicazione Spring MVC.
 * <p>
 * Questa classe implementa l'interfaccia {@link WebMvcConfigurer} per personalizzare
 * la configurazione MVC fornita da Spring. In particolare, registra un interceptor
 * personalizzato per gestire l'accesso basato sui ruoli a vari endpoints dell'applicazione.
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AccessInterceptor accessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor)
        
        .addPathPatterns("/admin/home",
                "/admin/profilo",
                "/admin/profilo/{username}",
                "/admin/cercatweet",
                "/admin/esplora",
                "/admin/categoria/{categoria}",
                "/home",
                "/profilo",
                "/profilo/{username}",
                "/followUnfollow"
                  );
             
    }


    }



