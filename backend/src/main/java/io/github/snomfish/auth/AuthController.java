package io.github.snomfish.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.snomfish.auth.dto.AuthResponse;
import io.github.snomfish.auth.dto.LoginRequest;
import io.github.snomfish.auth.dto.RegisterRequest;
import io.github.snomfish.security.jwt.JwtCookieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    

    private final AuthService authService;


    public AuthController(
        AuthService authService
    ) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
        @Valid @RequestBody(required = false) RegisterRequest request
    ) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        authService.register(request);
        return ResponseEntity.ok(new AuthResponse(
            "User registered successfully"
        ));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @Valid @RequestBody(required = false) LoginRequest request,
        HttpServletResponse response
    ) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        authService.login(request, response);
        return ResponseEntity.ok(new AuthResponse(
            "User logged in successfully"
        ));
    }


    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(
        @CookieValue(JwtCookieService.ACCESS_TOKEN_COOKIE) String accessToken,
        HttpServletResponse response
    ) {
        authService.logout(accessToken, response);
        return ResponseEntity.ok(new AuthResponse(
            "Logout successful"
        ));
    }

}
