package ru.agilix.bookstorage.dao;

import ru.agilix.bookstorage.domain.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> getByBookId(int bookId);

    Comment save(Comment comment);
}
