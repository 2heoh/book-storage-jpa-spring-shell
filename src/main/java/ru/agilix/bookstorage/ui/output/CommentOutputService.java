package ru.agilix.bookstorage.ui.output;

import ru.agilix.bookstorage.domain.Comment;

import java.util.List;

public interface CommentOutputService {

    String showCommentInfo(Comment comment);

    String showCommentDeletedMessage(int id);

    String showCommentNotFound(int id);

    String showCommentUpdated(Comment comment);
}
