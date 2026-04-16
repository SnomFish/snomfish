package io.github.snomfish.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    
    
    @Bean
    public JwtService jwtService(JwtProperties jwtProperties) {
        return new JwtService(jwtProperties);
    }


    @Bean
    JwtCookieService jwtCookieService() {
        return new JwtCookieService();
    }
}
