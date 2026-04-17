package io.github.snomfish.application.friend;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.snomfish.application.friend.dto.RemoveFriendRequest;
import io.github.snomfish.application.friend.dto.request.AcceptFriendRequest;
import io.github.snomfish.application.friend.dto.request.FriendRequest;
import io.github.snomfish.application.friend.dto.request.RejectFriendRequest;
import io.github.snomfish.application.friend.dto.response.FriendListResponse;
import io.github.snomfish.application.friend.dto.response.FriendRequestListResponse;
import io.github.snomfish.application.friend.dto.response.FriendResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class FriendController {
    

    private final FriendService userService;


    @GetMapping("/me/friends")
    public ResponseEntity<FriendListResponse> getFriends(
        Authentication authentication
    ) {
        return ResponseEntity.ok(userService.getFriends(authentication));
    }


    @GetMapping("/me/friend-requests")
    public ResponseEntity<FriendRequestListResponse> getFriendRequests(
        Authentication authentication
    ) {
        return ResponseEntity.ok(userService.getFriendRequests(authentication));
    }


    @PostMapping("/me/friend-requests")
    public ResponseEntity<FriendResponse> sendFriendRequest(
        Authentication authentication,
        @Valid @RequestBody FriendRequest request
    ) {
        userService.sendFriendRequest(authentication, request);
        return ResponseEntity.ok(new FriendResponse(
            "Sent friend request"
        ));
    }


    @PostMapping("/me/friend-requests/accept")
    public ResponseEntity<FriendResponse> acceptFriendRequest(
        Authentication authentication,
        @Valid @RequestBody AcceptFriendRequest request
    ) {
        userService.acceptFriendRequest(authentication, request);
        return ResponseEntity.ok(new FriendResponse(
            "Accepted friend request"
        ));
    }


    @PostMapping("/me/friend-requests/reject")
    public ResponseEntity<FriendResponse> rejectFriendRequest(
        Authentication authentication,
        @Valid @RequestBody RejectFriendRequest request
    ) {
        userService.rejectFriendRequest(authentication, request);
        return ResponseEntity.ok(new FriendResponse(
            "Rejected friend request"
        ));
    }


    @PostMapping("/me/friends")
    public ResponseEntity<FriendResponse> removeFriend(
        Authentication authentication,
        @Valid @RequestBody RemoveFriendRequest request
    ) {
        userService.removeFriend(authentication, request);
        return ResponseEntity.ok(new FriendResponse(
            "Deleted friend"
        ));
    }
}
