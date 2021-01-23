package ru.agilix.bookstorage.ui.output;

import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;

import java.util.List;

public interface BookOutputService {
    String showBooksList(List<Book> books);
    
    String showBookInfo(Book book);

    String showBookCreatedMessage(Book inserted);

    String showBookDeletedMessage(int id);

    String showBookNotFound(int id);

    String showListOfComments(List<Comment> comments);

}
