package io.github.snomfish.application.friend.dto.response;

import java.util.List;

import io.github.snomfish.application.friend.dto.summary.FriendSummary;

public record FriendListResponse(
    List<FriendSummary> user
) {}