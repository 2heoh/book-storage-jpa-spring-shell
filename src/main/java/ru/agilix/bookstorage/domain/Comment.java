package ru.agilix.bookstorage.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="text")
    private String text;

    @Column(name="author")
    private String author;

    @Column(name="book_id")
    private int bookId;

    @Column(name="date")
    private Timestamp date;
}
