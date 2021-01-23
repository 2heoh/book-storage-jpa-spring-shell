package ru.agilix.bookstorage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agilix.bookstorage.dao.BooksDao;
import ru.agilix.bookstorage.dao.BooksDaoJpa;
import ru.agilix.bookstorage.dao.CommentDao;
import ru.agilix.bookstorage.ui.output.CommentOutputService;

@Service
public class CLICommentsService implements CommentsService {
    private final CommentDao commentDao;
    private final CommentOutputService ui;
    private final InputService input;
    private BooksDao bookDao;

    public CLICommentsService(CommentDao commentDao, CommentOutputService ui, InputService input) {
        this.commentDao = commentDao;
        this.ui = ui;
        this.input = input;
    }



    @Override
    @Transactional
    public String addCommentByBookId(int bookId) {
        final var newComment = input.getNewComment(bookId);
        final var comment = commentDao.save(newComment);
        return ui.showCommentInfo(comment);
    }

    @Override
    @Transactional
    public String deleteComment(int id) {
        if (commentDao.getById(id) == null){
            return ui.showCommentNotFound(id);
        }

        commentDao.delete(id);
        return ui.showCommentDeletedMessage(id);
    }

    @Override
    @Transactional
    public String updateComment(int id) {
        final var comment = commentDao.getById(id);
        if (comment == null) {
            ui.showCommentNotFound(id);
        }

        final var updatedComment = input.getUpdatedComment(comment);
        final var savedComment = commentDao.save(updatedComment);
        return ui.showCommentUpdated(savedComment);
    }
}
