package org.example.librarymanagement.controllers;

import org.example.librarymanagement.entity.BorrowRecord;
import org.example.librarymanagement.service.BorrowRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowRecords")
public class BorrowRecordController {
    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }


    // Members can borrow books
    @PostMapping("/borrow")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestBody BorrowRecord borrowRecord,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return ResponseEntity.ok(borrowRecordService.borrowBook(borrowRecord, username));
    }

    // Members can return their own books, librarians can force return
    @PutMapping("/return/{id}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('LIBRARIAN')")
    public ResponseEntity<BorrowRecord> returnBook(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(borrowRecordService.returnBook(id, userDetails.getUsername()));
    }

    // Admins can view all records
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowRecord>> getAllRecords() {
        return ResponseEntity.ok(borrowRecordService.getAllRecords());
    }

    // Members can view their own records
    @GetMapping("/myRecords")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<List<BorrowRecord>> getMyRecords(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(borrowRecordService.getRecordsByUser(userDetails.getUsername()));
    }

    // Librarians can manage (create) borrow records for members
    @PostMapping("/librarian/assign")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BorrowRecord> assignBookToMember(
            @RequestBody BorrowRecord borrowRecord) {
        return ResponseEntity.ok(borrowRecordService.createBorrowRecord(borrowRecord));
    }
}
