package com.example.projectTwitter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//configurazione che ci permette di usare PasswordEncoder per operazioni di hashing o confronto della password


@Configuration
public class SecurityConfig {
	
	/**
     * Crea un bean che fornisce un encoder per le password basato sull'algoritmo di hashing BCrypt.
     * Questo encoder è utilizzato per hashare le password prima di memorizzarle nel database e per verificare le password durante l'autenticazione.
     *
     * @return Un'istanza di  BCryptPasswordEncoder da utilizzare come encoder delle password dell'applicazione.
     */
	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
