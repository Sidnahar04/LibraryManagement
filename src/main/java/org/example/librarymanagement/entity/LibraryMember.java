package org.example.librarymanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LibraryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    private Date membershipDate;

    @OneToOne(mappedBy = "libraryMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private MembershipCard membershipCard;

    @OneToMany(mappedBy = "libraryMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowedBooks;
}
