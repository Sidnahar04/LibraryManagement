package org.example.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.librarymanagement.entity.Book;
import org.example.librarymanagement.entity.LibraryMember;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("borrow_date")
    private Date borrowDate;

    @JsonProperty("return_date")
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "library_member_id")
    @JsonBackReference
    private LibraryMember libraryMember;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
