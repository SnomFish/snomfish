package io.github.snomfish.auth.dto;

public record LoginRequest(
    String username,
    String password
) {}