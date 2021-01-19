package ru.agilix.bookstorage.ui;

import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

public interface MessageCreatorService {
    String showBooksList(List<Book> books);
    
    String showBookInfo(Book book);

    String showBookCreatedMessage(Book inserted);

    String showBookDeletedMessage(int id);

    String showBookNotFound(int id);

    String showAuthorsList(List<Author> list);

    String showEmptyAuthorsList();

    String showGenreList(List<Genre> genres);

    String showListOfComments(List<Comment> comments);

    String showCommentInfo(Comment comment);
}
