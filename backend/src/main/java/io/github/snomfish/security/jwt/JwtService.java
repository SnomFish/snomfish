package io.github.snomfish.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    
    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;


    public JwtService(
        JwtProperties jwtProperties
    ) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }


    public String generateAccessToken(
        String username,
        List<String> roles,
        String sessionId
    ) {
        
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getAccessTokenExpirationMs());

        return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .claim("sid", sessionId)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }


    public String generateRefreshToken(
        String username
    ) {
        
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getAccessTokenExpirationMs());

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }


    public Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token);
    }


    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;

        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }


    // EXTRACTION
    public String extractUsername(String token) {
        return parseToken(token)
            .getBody()
            .getSubject();
    }
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return parseToken(token)
            .getBody()
            .get("role", List.class);
    }
    public String extractSessionId(String token) {
        return parseToken(token)
            .getBody()
            .get("sid", String.class);
    }
}
