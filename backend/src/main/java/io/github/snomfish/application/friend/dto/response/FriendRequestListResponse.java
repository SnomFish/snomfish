package io.github.snomfish.application.friend.dto.response;

import java.util.List;

import io.github.snomfish.application.friend.dto.summary.FriendRequestSummary;

public record FriendRequestListResponse (
    List<FriendRequestSummary> friendRequests
) {}