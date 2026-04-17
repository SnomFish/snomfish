package io.github.snomfish.application.auth;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.snomfish.application.auth.dto.LoginRequest;
import io.github.snomfish.application.auth.dto.RegisterRequest;
import io.github.snomfish.persistence.role.Role;
import io.github.snomfish.persistence.role.RoleRepository;
import io.github.snomfish.persistence.user.User;
import io.github.snomfish.persistence.user.UserRepository;
import io.github.snomfish.security.jwt.JwtCookieService;
import io.github.snomfish.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtCookieService jwtCookieService;


    public void register(
        RegisterRequest request
    ) {

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role userRole = roleRepository.findByName("USER")
            .orElseThrow(() -> new IllegalStateException("role \'USER\' not found"));
        
        User user = new User();
        user.setUsername(request.username());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setCreatedAt(Instant.now());
        user.getRoles().add(userRole);
        userRepository.save(user);
    }


    public void login(
        LoginRequest request, 
        HttpServletResponse response
    ) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        var roles = user.getRoles().stream()
            .map(Role::getName)
            .toList();
        String sessionId = UUID.randomUUID().toString();
        String accessToken = jwtService.generateAccessToken(
            user.getUsername(),
            roles,
            sessionId
        );
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }

        jwtCookieService.addAccessTokenCookie(response, accessToken);
        jwtCookieService.addRefreshTokenCookie(response, refreshToken);
    }


    public void logout(
        String accessToken,
        HttpServletResponse response
    ) {
        jwtCookieService.clearCookies(response);
    }
}
