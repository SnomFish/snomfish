package io.github.snomfish.application.auth.dto;

public record LoginRequest(
    String username,
    String password
) {}