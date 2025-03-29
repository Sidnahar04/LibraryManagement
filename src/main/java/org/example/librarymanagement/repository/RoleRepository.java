package org.example.librarymanagement.repository;

import org.example.librarymanagement.entity.ERole;
import org.example.librarymanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
