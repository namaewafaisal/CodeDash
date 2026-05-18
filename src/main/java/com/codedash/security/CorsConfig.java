package com.codedash.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // which frontends can talk to this backend
        config.setAllowedOrigins(List.of(
            "http://localhost:5173",      // local dev
            "https://your-app.vercel.app" // production (add later)
        ));

        // which HTTP methods are allowed
        config.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // allow all headers (including Authorization for JWT)
        config.setAllowedHeaders(List.of("*"));

        // needed for Authorization header to work
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        // apply to all endpoints
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}