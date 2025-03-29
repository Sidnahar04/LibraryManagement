package org.example.librarymanagement.service;

import org.example.librarymanagement.entity.Book;
import org.example.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BorrowRecordService borrowRecordService;

    public BookService(BookRepository bookRepository,BorrowRecordService borrowRecordService) {
        this.bookRepository = bookRepository;
        this.borrowRecordService = borrowRecordService;
    }

    //  Get all books
    public List<Book> getAllBooks(boolean isAuthenticated) {
        List<Book> allBooks = bookRepository.findAll();

        if (isAuthenticated) {
            return allBooks; // Authenticated users see all books
        } else {
            // Return only books that are NOT borrowed
            List<Long> borrowedBookIds = borrowRecordService.getCurrentlyBorrowedBookIds();
            return allBooks.stream()
                    .filter(book -> !borrowedBookIds.contains(book.getId()))
                    .collect(Collectors.toList());
        }
    }

    // Get a book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    //  Create a new book
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    //  Update a book
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setIsbn(updatedBook.getIsbn());
            book.setPublicationYear(updatedBook.getPublicationYear());
            book.setAuthors(updatedBook.getAuthors());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // ✅ Delete a book only if it is NOT borrowed
    public void deleteBook(Long id) {
        if (borrowRecordService.isBookCurrentlyBorrowed(id)) {
            throw new RuntimeException("Book is currently borrowed and cannot be deleted.");
        }
        bookRepository.deleteById(id);

    }
}
