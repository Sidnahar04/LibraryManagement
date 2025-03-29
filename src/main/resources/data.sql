

-- Insert Authors (Famous Authors in DSA)
INSERT INTO author (name, biography) VALUES
                                         ('Thomas H. Cormen', 'Co-author of Introduction to Algorithms, known for CLRS.'),
                                         ('Robert Sedgewick', 'Known for his work in algorithms and data structures.'),
                                         ('Donald Knuth', 'Creator of The Art of Computer Programming.'),
                                         ('Steven S. Skiena', 'Author of The Algorithm Design Manual.'),
                                         ('Jon Kleinberg', 'Author and professor specializing in network algorithms.');

-- Insert Books (Books Related to Data Structures and Algorithms)
INSERT INTO book (title, isbn, publication_year) VALUES
                                                     ('Introduction to Algorithms', '9780262033848', 2009),
                                                     ('Algorithms', '9780321573513', 2011),
                                                     ('The Art of Computer Programming', '9780201896831', 1997),
                                                     ('The Algorithm Design Manual', '9781848000698', 2008),
                                                     ('Algorithm Design', '9780321295354', 2005);

-- Link Books and Authors (Many-to-Many Relationship)
INSERT INTO book_author (book_id, author_id) VALUES
                                                 (1, 1), -- Introduction to Algorithms → Thomas H. Cormen
                                                 (2, 2), -- Algorithms → Robert Sedgewick
                                                 (3, 3), -- The Art of Computer Programming → Donald Knuth
                                                 (4, 4), -- The Algorithm Design Manual → Steven S. Skiena
                                                 (5, 5), -- Algorithm Design → Jon Kleinberg
                                                 (1, 3), -- Introduction to Algorithms also has Donald Knuth as contributor
                                                 (2, 4); -- Algorithms also has Steven S. Skiena as contributor

-- Insert Library Members
INSERT INTO library_member (name, email, membership_date) VALUES
                                                              ('Alice Williams', 'alice@example.com', '2023-02-10'),
                                                              ('Bob Johnson', 'bob@example.com', '2023-05-21'),
                                                              ('Charlie Brown', 'charlie@example.com', '2023-07-15'),
                                                              ('David Smith', 'david@example.com', '2023-08-30'),
                                                              ('Emma Wilson', 'emma@example.com', '2023-11-05');

-- Insert Membership Cards (Each Member Gets a Unique Card)
INSERT INTO membership_card (card_number, issue_date, expiry_date, library_member_id) VALUES
                                                                                          ('CARD1001', '2023-02-10', '2024-02-10', 1),
                                                                                          ('CARD1002', '2023-05-21', '2024-05-21', 2),
                                                                                          ('CARD1003', '2023-07-15', '2024-07-15', 3),
                                                                                          ('CARD1004', '2023-08-30', '2024-08-30', 4),
                                                                                          ('CARD1005', '2023-11-05', '2024-11-05', 5);

-- Insert Borrow Records (Tracking Which Books Are Borrowed)
INSERT INTO borrow_record (borrow_date, return_date, library_member_id, book_id) VALUES
                                                                                     ('2024-03-10', NULL, 1, 1), -- Alice borrowed "Introduction to Algorithms"
                                                                                     ('2024-03-15', '2024-03-25', 2, 2), -- Bob borrowed "Algorithms" and returned it
                                                                                     ('2024-03-20', NULL, 3, 3), -- Charlie borrowed "The Art of Computer Programming"
                                                                                     ('2024-03-25', NULL, 4, 4), -- David borrowed "The Algorithm Design Manual"
                                                                                     ('2024-03-28', '2024-04-10', 5, 5); -- Emma borrowed "Algorithm Design" and returned it
