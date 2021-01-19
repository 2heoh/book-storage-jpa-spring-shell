package ru.agilix.bookstorage.dao.dsl;

import ru.agilix.bookstorage.dao.dsl.BookBuilder;
import ru.agilix.bookstorage.domain.Comment;

public class Create {
    public static BookBuilder Book() {
        return new BookBuilder();
    }

    public static CommentBuilder Comment() {
        return new CommentBuilder();
    }
}
