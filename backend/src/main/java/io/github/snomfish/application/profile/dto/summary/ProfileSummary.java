package io.github.snomfish.application.profile.dto.summary;

import java.time.Instant;

public record ProfileSummary (
    String username,
    Instant createdAt
) {}