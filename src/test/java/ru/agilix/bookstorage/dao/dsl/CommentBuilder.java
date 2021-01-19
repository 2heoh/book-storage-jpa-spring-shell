package ru.agilix.bookstorage.dao.dsl;

import ru.agilix.bookstorage.domain.Comment;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentBuilder {
    private int id;
    private String text = "nothing";
    private int bookId;
    private Timestamp date;
    private String author = "no name";

    public CommentBuilder Id(int id) {
        this.id = id;
        return this;
    }

    public CommentBuilder Text(String text) {
        this.text = text;
        return this;
    }

    public CommentBuilder BookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    public CommentBuilder Date(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parsedDate = dateFormat.parse(date);
        this.date = new java.sql.Timestamp(parsedDate.getTime());
        return this;
    }

    public Comment build() {
        return new Comment(id, text, author, bookId, date);
    }

    public CommentBuilder Author(String author) {
        this.author = author;
        return this;
    }
}
