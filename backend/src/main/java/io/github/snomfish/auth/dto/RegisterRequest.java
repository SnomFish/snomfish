package io.github.snomfish.auth.dto;

public record RegisterRequest(
    String username,
    String password
) {}