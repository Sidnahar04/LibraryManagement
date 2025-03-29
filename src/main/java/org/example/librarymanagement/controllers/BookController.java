package org.example.librarymanagement.controllers;

import org.example.librarymanagement.entity.Book;
import org.example.librarymanagement.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Book>> getAllBooks() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());

        List<Book> books = bookService.getAllBooks(isAuthenticated);
        return ResponseEntity.ok(books);
    }

    //Get book by ID
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create book
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    // Update book
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return ResponseEntity.ok(bookService.updateBook(id, updatedBook));
    }

    // Delete book only if NOT borrowed
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
