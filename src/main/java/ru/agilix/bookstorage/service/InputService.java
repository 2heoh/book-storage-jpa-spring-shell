package ru.agilix.bookstorage.service;

import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

public interface InputService {
    Book getUpdatedBookInfo(Book existingBook, List<Author> authors, List<Genre> genres);

    Book getNewBook(List<Author> authors, List<Genre> genres);

    Comment getNewComment(int bookId);

    Comment getUpdatedComment(Comment comment);
}
