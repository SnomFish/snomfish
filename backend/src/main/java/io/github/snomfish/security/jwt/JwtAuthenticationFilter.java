package io.github.snomfish.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    
    private final JwtService jwtService;
    private final JwtCookieService jwtCookieService;
    private final UserDetailsService userDetailsService;


    public JwtAuthenticationFilter(
        JwtService jwtService,
        JwtCookieService jwtCookieService,
        UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.jwtCookieService = jwtCookieService;
        this.userDetailsService = userDetailsService;
    }
    

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String token = extractTokenFromCookie(request);

            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                String username = jwtService.extractUsername(token);
                var userDetails = userDetailsService.loadUserByUsername(username);
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }


        } catch (Exception e) { // there were many esceptions, but they all lead to the same outcome.
            jwtCookieService.clearAccessTokenCookie(response);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }


    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (JwtCookieService.ACCESS_TOKEN_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
