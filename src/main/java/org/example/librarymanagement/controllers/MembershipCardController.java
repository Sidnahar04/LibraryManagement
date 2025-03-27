package org.example.librarymanagement.controllers;

import jakarta.validation.Valid;
import org.example.librarymanagement.entity.MembershipCard;
import org.example.librarymanagement.service.MembershipCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/membershipcards")
public class MembershipCardController {

    @Autowired
    private final MembershipCardService membershipCardService;

    public MembershipCardController(MembershipCardService membershipCardService) {
        this.membershipCardService = membershipCardService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipCard>> getAllCards() {
        return ResponseEntity.ok(membershipCardService.getAllCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipCard> getCardById(@PathVariable Long id) {
        Optional<MembershipCard> card = membershipCardService.getCardById(id);
        return card.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MembershipCard> createCard(@RequestBody @Valid MembershipCard card) {
        // Ensure card is created properly with the service method
        MembershipCard savedCard = membershipCardService.createCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MembershipCard> updateCard(@PathVariable Long id,
                                                     @RequestBody @Valid MembershipCard updatedCard) {
        updatedCard.setId(id); // Ensure the correct ID is set for the update operation
        MembershipCard updated = membershipCardService.updateCard(id, updatedCard);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        membershipCardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
