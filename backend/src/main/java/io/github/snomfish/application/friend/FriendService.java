package io.github.snomfish.application.friend;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.github.snomfish.application.friend.dto.RemoveFriendRequest;
import io.github.snomfish.application.friend.dto.request.AcceptFriendRequest;
import io.github.snomfish.application.friend.dto.request.FriendRequest;
import io.github.snomfish.application.friend.dto.request.RejectFriendRequest;
import io.github.snomfish.application.friend.dto.response.FriendListResponse;
import io.github.snomfish.application.friend.dto.response.FriendRequestListResponse;
import io.github.snomfish.application.friend.dto.summary.FriendRequestSummary;
import io.github.snomfish.application.friend.dto.summary.FriendSummary;
import io.github.snomfish.persistence.user.User;
import io.github.snomfish.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FriendService {
    

    private final UserRepository userRepository;
    

    public FriendListResponse getFriends(
        Authentication authentication
    ) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return new FriendListResponse(
            user.getFriends().stream()
                .map(u -> new FriendSummary(
                    u.getUsername(),
                    u.getCreatedAt()
                ))
                .toList()
        );
    }


    public FriendRequestListResponse getFriendRequests(
        Authentication authentication
    ) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return new FriendRequestListResponse(
            user.getFriendRequests().stream()
                .map(u -> new FriendRequestSummary(
                    u.getUsername()
                ))
                .toList()
        );
    }


    /* Checks:
    - request exists
    - reqUser exists
    - reqUser already has user in friends
    - reqUser already has user in friend requests
    */
    public void sendFriendRequest(
        Authentication authentication,
        FriendRequest request
    ) {
        String username = authentication.getName();
        String reqUsername = request.username();
        if (reqUsername == null) {throw new IllegalArgumentException("Friend request cannot be empty");}
        if (!userRepository.existsByUsername(reqUsername)) {throw new IllegalArgumentException("User cannot be found");}

        User user = userRepository.findByUsername(username).orElseThrow();
        User reqUser = userRepository.findByUsername(reqUsername).orElseThrow();

        if (reqUser.friendsContain(user)) {throw new IllegalArgumentException("Already friends with user");}
        if (reqUser.friendRequestsContain(user)) {throw new IllegalArgumentException("Already sent friend request");}

        reqUser.getFriendRequests().add(user);
    }


    /* Checks:
    - request exists
    - reqUser exists
    - user has reqUser in friendRequests
    - user does not have reqUser in friends
    */
    public void acceptFriendRequest(
        Authentication authentication,
        AcceptFriendRequest request
    ) {
        String username = authentication.getName();
        String reqUsername = request.username();
        if (reqUsername == null) {throw new IllegalArgumentException("Friend request cannot be empty");}
        if (!userRepository.existsByUsername(reqUsername)) {throw new IllegalArgumentException("User cannot be found");}

        User user = userRepository.findByUsername(username).orElseThrow();
        User reqUser = userRepository.findByUsername(reqUsername).orElseThrow();

        if (!user.friendRequestsContain(reqUser)) {throw new IllegalArgumentException("User not in friend requests");}
        if (user.friendsContain(reqUser)) {throw new IllegalArgumentException("User is already a friend");}

        user.removeFriendRequest(reqUser); // removes
        reqUser.removeFriendRequest(user);
        user.getFriends().add(reqUser);
        reqUser.getFriends().add(user);
    }


    /* Checks:
    - request exists
    - reqUser exists
    - user has reqUser in friendRequests
    */
    public void rejectFriendRequest(
        Authentication authentication,
        RejectFriendRequest request
    ) {
        String username = authentication.getName();
        String reqUsername = request.username();
        if (reqUsername == null) {throw new IllegalArgumentException("Friend request cannot be empty");}
        if (!userRepository.existsByUsername(reqUsername)) {throw new IllegalArgumentException("User cannot be found");}

        User user = userRepository.findByUsername(username).orElseThrow();
        User reqUser = userRepository.findByUsername(reqUsername).orElseThrow();

        if (!user.friendRequestsContain(reqUser)) {throw new IllegalArgumentException("User not in friend requests");}
        // maybe a check to see if the reqUser is in the user's friends list?

        user.removeFriendRequest(reqUser);
        reqUser.removeFriendRequest(user);
    }


    /* Checks:
    - request exists
    - reqUser exists
    */
    public void removeFriend(
        Authentication authentication,
        RemoveFriendRequest request
    ) {
        String username = authentication.getName();
        String reqUsername = request.username();
        if (reqUsername == null) {throw new IllegalArgumentException("Friend request cannot be empty");}
        if (!userRepository.existsByUsername(reqUsername)) {throw new IllegalArgumentException("User cannot be found");}

        User user = userRepository.findByUsername(username).orElseThrow();
        User reqUser = userRepository.findByUsername(reqUsername).orElseThrow();

        // not checking if the user has reqUser in friends as reqUser may have user in friends :/
        user.removeFriend(reqUser);
        reqUser.removeFriend(user);
    }
}
