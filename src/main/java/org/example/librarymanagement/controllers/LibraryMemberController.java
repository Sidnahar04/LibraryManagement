package org.example.librarymanagement.controllers;

import org.example.librarymanagement.entity.LibraryMember;
import org.example.librarymanagement.service.LibraryMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
@Validated
public class LibraryMemberController {
    private final LibraryMemberService libraryMemberService;

    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<LibraryMember>> getAllMembers() {
        List<LibraryMember> members = libraryMemberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<LibraryMember> getMemberById(@PathVariable Long id) {
        Optional<LibraryMember> member = libraryMemberService.getMemberById(id);
        return member.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibraryMember> createMember(@RequestBody LibraryMember member) {
            return ResponseEntity.ok(libraryMemberService.createMember(member));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibraryMember> updateMember(@PathVariable Long id, @RequestBody LibraryMember updatedMember) {
        return ResponseEntity.ok(libraryMemberService.updateMember(id, updatedMember));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        libraryMemberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

}
