package org.example.librarymanagement.controllers;

import org.example.librarymanagement.entity.BorrowRecord;
import org.example.librarymanagement.service.BorrowRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrowRecords")
public class BorrowRecordController {
    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecord> borrowBook(@RequestBody BorrowRecord borrowRecord) {
        return ResponseEntity.ok(borrowRecordService.borrowBook(borrowRecord));
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowRecordService.returnBook(id));
    }
}
