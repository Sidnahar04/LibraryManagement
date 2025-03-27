package org.example.librarymanagement.service;

import org.example.librarymanagement.entity.MembershipCard;
import org.example.librarymanagement.repository.MembershipCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipCardService {
    private final MembershipCardRepository membershipCardRepository;
    public MembershipCardService(MembershipCardRepository membershipCardRepository) {
        this.membershipCardRepository = membershipCardRepository;
    }

    public List<MembershipCard> getAllCards() {
        return membershipCardRepository.findAll();
    }

    public Optional<MembershipCard> getCardById(Long id) {
        return membershipCardRepository.findById(id);
    }

    public MembershipCard createCard(MembershipCard card) {
        return membershipCardRepository.save(card);
    }

    // Update an existing MembershipCard
    public MembershipCard updateCard(Long id, MembershipCard updatedCard) {
        // Check if the card exists
        if (membershipCardRepository.existsById(id)) {
            updatedCard.setId(id);  // Ensure the ID is correctly set
            return membershipCardRepository.save(updatedCard);
        } else {
            try {
                throw new Exception("MembershipCard not found with id " + id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteCard(Long id) {
        MembershipCard membershipCard = null;
        try {
            membershipCard = membershipCardRepository.findById(id)
                    .orElseThrow(() -> new Exception("Card not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Only delete the membership card, not the member
        membershipCardRepository.delete(membershipCard);
    }
}
