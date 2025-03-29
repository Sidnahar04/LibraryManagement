package org.example.librarymanagement.service;

import jakarta.transaction.Transactional;
import org.example.librarymanagement.entity.BorrowRecord;
import org.example.librarymanagement.exception.NotFoundException;
import org.example.librarymanagement.exception.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;

import org.example.librarymanagement.entity.LibraryMember;
import org.example.librarymanagement.entity.User;
import org.example.librarymanagement.repository.*;

import java.util.Date;
import java.util.List;

@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final UserRepository userRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository,
                               LibraryMemberRepository libraryMemberRepository,
                               UserRepository userRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BorrowRecord borrowBook(BorrowRecord borrowRecord, String username) {
        // Verify the requesting member matches the borrow record member
        LibraryMember member = libraryMemberRepository.findByUserEmail(username)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        if (!borrowRecord.getLibraryMember().getId().equals(member.getId())) {
            throw new UnauthorizedAccessException("You can only borrow books for yourself");
        }

        if (isBookCurrentlyBorrowed(borrowRecord.getBook().getId())) {
            throw new IllegalStateException("Book is already borrowed");
        }

        borrowRecord.setBorrowDate(new Date());
        borrowRecord.setReturnDate(null);
        return borrowRecordRepository.save(borrowRecord);
    }

    @Transactional
    public BorrowRecord returnBook(Long recordId, String username) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("Borrow record not found"));

        // Librarians can force return, members can only return their own books
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getRoles().stream().noneMatch(role -> role.getName().name().equals("ROLE_LIBRARIAN")) &&
                !record.getLibraryMember().getUser().getEmail().equals(username)) {
            throw new UnauthorizedAccessException("You can only return your own books");
        }

        if (record.getReturnDate() != null) {
            throw new IllegalStateException("Book has already been returned");
        }

        record.setReturnDate(new Date());
        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getAllRecords() {
        return borrowRecordRepository.findAll();
    }

    public List<BorrowRecord> getRecordsByUser(String username) {
        LibraryMember member = libraryMemberRepository.findByUserEmail(username)
                .orElseThrow(() -> new NotFoundException("Member not found"));
        return borrowRecordRepository.findByLibraryMember(member);
    }

    @Transactional
    public BorrowRecord createBorrowRecord(BorrowRecord borrowRecord) {
        // Librarian-specific method - no additional checks needed as controller is secured
        if (isBookCurrentlyBorrowed(borrowRecord.getBook().getId())) {
            throw new IllegalStateException("Book is already borrowed");
        }

        borrowRecord.setBorrowDate(new Date());
        borrowRecord.setReturnDate(null);
        return borrowRecordRepository.save(borrowRecord);
    }

    public boolean isBookCurrentlyBorrowed(Long bookId) {
        return !borrowRecordRepository.findByBookIdAndReturnDateIsNull(bookId).isEmpty();
    }
}