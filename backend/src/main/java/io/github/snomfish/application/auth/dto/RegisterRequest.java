package io.github.snomfish.application.auth.dto;

public record RegisterRequest(
    String username,
    String password
) {}