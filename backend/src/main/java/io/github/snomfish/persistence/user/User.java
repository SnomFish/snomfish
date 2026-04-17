package io.github.snomfish.persistence.user;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.github.snomfish.persistence.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_friend_requests",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "request_id")
    )
    private Set<User> friendRequests = new HashSet<>();
    

    // CONSTRUCTORS
    public User() {}


    // GETTERS
    public long getId() {return id;}
    public String getUsername() {return username;}
    public String getPasswordHash() {return passwordHash;}
    public Instant getCreatedAt() {return createdAt;}
    public Set<Role> getRoles() {return roles;}
    public Set<User> getFriends() {return friends;}
    public Set<User> getFriendRequests() {return friendRequests;}


    // SETTERS
    public void setId(long id) {this.id = id;}
    public void setUsername(String username) {this.username = username;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}
    public void setCreatedAt(Instant createdAt) {this.createdAt = createdAt;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
    public void setFriends(Set<User> friends) {this.friends = friends;}
    public void setFriendRequests(Set<User> friendRequests) {this.friendRequests = friendRequests;}


    // HELPERS
    public boolean friendsContain(User user) {
        return friends.stream()
            .anyMatch(u -> u.getUsername().equals(user.getUsername()));
    }
    public boolean friendRequestsContain(User user) {
        return friendRequests.stream()
            .anyMatch(u -> u.getUsername().equals(user.getUsername()));
    }
    public void removeFriend(User user) {
        friends.removeIf(u -> u.getUsername().equals(user.getUsername()));
    }
    public void removeFriendRequest(User user) {
        friendRequests.removeIf(u -> u.getUsername().equals(user.getUsername()));
    }
}
