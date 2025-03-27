package org.example.librarymanagement.service;

import org.example.librarymanagement.entity.Author;
import org.example.librarymanagement.repository.AuthorRepository;
import org.example.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    // Create Author
    public Author createAuthor(Author author) {
        // Check if the author is already linked to any books before creating
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new IllegalStateException("Cannot create author, as they are linked to books.");
        }
        return authorRepository.save(author);
    }

    // Read All Authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Get Author by ID
    public Author getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    // Update Author
    public Author updateAuthor(Long id, Author updatedAuthor) {
        // Check if the author exists
        Optional<Author> existingAuthorOptional = authorRepository.findById(id);
        if (existingAuthorOptional.isEmpty()) {
            return null;
        }

        Author existingAuthor = existingAuthorOptional.get();

        // Check if the author is linked to any books before allowing update
        if (!existingAuthor.getBooks().isEmpty()) {
            throw new IllegalStateException("Cannot update author, as they are linked to books.");
        }

        // Allow update if the author is not linked to any books
        updatedAuthor.setId(id);
        return authorRepository.save(updatedAuthor);
    }

    // Delete Author
    public boolean deleteAuthor(Long id) {
        // Check if the author is linked to any books before deleting
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            return false;
        }

        Author author = authorOptional.get();

        // If the author is linked to any books, prevent deletion
        if (!author.getBooks().isEmpty()) {
            throw new IllegalStateException("Cannot delete author, as they are linked to books.");
        }

        authorRepository.deleteById(id);
        return true;
    }
}
