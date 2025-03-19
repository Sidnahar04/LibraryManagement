package org.example.librarymanagement.repository;

import org.example.librarymanagement.entity.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipCardRepository extends JpaRepository<MembershipCard, Long> {}
