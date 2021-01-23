package ru.agilix.bookstorage.service;

public interface CommentsService {
    String addCommentByBookId(int bookId);

    String deleteComment(int id);

    String updateComment(int id);

}
