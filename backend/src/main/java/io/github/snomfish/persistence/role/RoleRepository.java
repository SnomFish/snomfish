package io.github.snomfish.persistence.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    

    boolean existsByName(String name);


    void deleteByName(String name);


    Optional<Role> findByName(String name);
}
