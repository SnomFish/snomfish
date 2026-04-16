package io.github.snomfish.security;

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


        config.setAllowedOrigins(List.of(
            "http://localhost:80", // frontend
            "http://localhost:8080", // backend
            "http://localhost:5432" // db
        ));


        config.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "DELETE"
        ));


        config.setAllowedHeaders(List.of(
            "Authorisation",
            "Content-Type"
        ));


        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
