package org.example.librarymanagement.service;

import org.example.librarymanagement.entity.LibraryMember;
import org.example.librarymanagement.entity.MembershipCard;
import org.example.librarymanagement.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryMemberService {
    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public List<LibraryMember> getAllMembers() {
        return libraryMemberRepository.findAll();
    }

    public Optional<LibraryMember> getMemberById(Long id) {
        return libraryMemberRepository.findById(id);
    }

    public LibraryMember createMember(LibraryMember member) {
        member.setName(member.getName());
        member.setEmail(member.getEmail());
        member.setMembershipDate(LocalDate.now());

        MembershipCard card = new MembershipCard();
        card.setCardNumber(generateCardNumber()); // Assume you have a method to generate a card number
        card.setIssueDate(LocalDate.now());
        card.setExpiryDate(LocalDate.now().plusYears(1));
        card.setLibraryMember(member);

        member.setMembershipCard(card);
        return libraryMemberRepository.save(member);
    }

    public LibraryMember updateMember(Long id, LibraryMember updatedMember) {
        return libraryMemberRepository.findById(id).map(member -> {
            member.setName(updatedMember.getName());
            member.setEmail(updatedMember.getEmail());
            return libraryMemberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public void deleteMember(Long id) {
        libraryMemberRepository.deleteById(id);
    }

    private String generateCardNumber() {
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        return "CARD" + randomNumber;
    }
}
