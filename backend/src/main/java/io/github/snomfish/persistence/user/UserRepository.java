package io.github.snomfish.persistence.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    

    boolean existsByUsername(String username);


    void deleteByUsername(String username);


    @EntityGraph(attributePaths = {"friends", "friendRequests"})
    Optional<User> findByUsername(String username);
}
