package io.github.snomfish.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    

    boolean existsByUsername(String username);


    void deleteByUsername(String username);


    Optional<User> findByUsername(String username);
}
