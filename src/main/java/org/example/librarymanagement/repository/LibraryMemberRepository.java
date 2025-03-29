package org.example.librarymanagement.repository;

import org.example.librarymanagement.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
    Optional<LibraryMember> findByUserEmail(String email);
}
