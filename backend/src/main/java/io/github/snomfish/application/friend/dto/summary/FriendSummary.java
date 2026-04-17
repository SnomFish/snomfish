package io.github.snomfish.application.friend.dto.summary;

import java.time.Instant;

public record FriendSummary(
    String username,
    Instant createdAt
) {}