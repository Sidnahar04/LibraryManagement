package org.example.librarymanagement.service;

import jakarta.transaction.Transactional;
import org.example.librarymanagement.entity.BorrowRecord;
import org.example.librarymanagement.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @Transactional
    public BorrowRecord borrowBook(BorrowRecord borrowRecord) {
        borrowRecord.setBorrowDate(new Date());
        borrowRecord.setReturnDate(null); // Book is currently borrowed
        return borrowRecordRepository.save(borrowRecord);
    }

    @Transactional
    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getReturnDate() != null) {
            throw new RuntimeException("Book has already been returned.");
        }

        record.setReturnDate(new Date()); // Mark as returned
        return borrowRecordRepository.save(record);
    }

    public boolean isBookCurrentlyBorrowed(Long bookId) {
        return !borrowRecordRepository.findByBookIdAndReturnDateIsNull(bookId).isEmpty();
    }
}
