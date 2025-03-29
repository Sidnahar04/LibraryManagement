package org.example.librarymanagement.repository;


import org.example.librarymanagement.entity.ERole;
import org.example.librarymanagement.entity.Role;
import org.example.librarymanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}

