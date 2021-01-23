package ru.agilix.bookstorage.dao;

import ru.agilix.bookstorage.domain.Comment;

import java.util.List;

public interface CommentDao {
    Comment save(Comment comment);

    void delete(int id);

    Comment getById(int id);
}
