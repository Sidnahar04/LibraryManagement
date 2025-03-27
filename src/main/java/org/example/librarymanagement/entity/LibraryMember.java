package org.example.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "library_member")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LibraryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "Name is required")
    @JsonProperty("name")
    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("membership_date")
    private LocalDate membershipDate;

    @OneToOne(mappedBy = "libraryMember", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private MembershipCard membershipCard;

    @OneToMany(mappedBy = "libraryMember", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BorrowRecord> borrowedBooks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    public List<BorrowRecord> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowRecord> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
