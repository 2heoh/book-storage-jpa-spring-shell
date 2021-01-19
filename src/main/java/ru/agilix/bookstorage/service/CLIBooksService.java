package ru.agilix.bookstorage.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agilix.bookstorage.dao.AuthorDao;
import ru.agilix.bookstorage.dao.BooksDao;
import ru.agilix.bookstorage.dao.CommentDao;
import ru.agilix.bookstorage.dao.GenreDao;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.ui.MessageCreatorService;

import java.util.List;

@Service
public class CLIBooksService implements BooksService {
    private final BooksDao booksDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final CommentDao commentDao;
    private final MessageCreatorService ui;
    private final InputService input;

    public CLIBooksService(BooksDao booksDao, MessageCreatorService ui, AuthorDao authorDao, GenreDao genreDao, CommentDao commentDao, InputService input) {
        this.booksDao = booksDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.commentDao = commentDao;
        this.ui = ui;
        this.input = input;
    }

    @Override
    @Transactional
    public String updateBook(int id) {
        Book existingBook = booksDao.getById(id);
        Book updatedBook = input.getUpdatedBookInfo(existingBook, authorDao.getAll(), genreDao.getAll());
        booksDao.save(updatedBook);
        return ui.showBookInfo(updatedBook);
    }

    @Override
    @Transactional
    public String retrieveAllBooks() {
        List<Book> bookList = booksDao.getAll();
        return ui.showBooksList(bookList);
    }

    @Override
    @Transactional
    public String createBook() {
        Book book = input.getNewBook(authorDao.getAll(), genreDao.getAll());
        Book inserted = booksDao.save(book);
        return ui.showBookCreatedMessage(inserted);
    }

    @Override
    @Transactional(readOnly = true)
    public String retrieveBook(int id) {
        try {
            Book book = booksDao.getById(id);
            return ui.showBookInfo(book);
        } catch (EmptyResultDataAccessException e) {
            return ui.showBookNotFound(id);
        }
    }

    @Override
    @Transactional
    public String deleteBook(int id) {

        if (booksDao.getById(id) == null)
            return ui.showBookNotFound(id);

        booksDao.delete(id);

        return ui.showBookDeletedMessage(id);
    }

    @Override
    @Transactional(readOnly = true)
    public String showAllAuthors() {
        List<Author> authors = authorDao.getAll();

        if (authors.isEmpty())
            return ui.showEmptyAuthorsList();

        return ui.showAuthorsList(authors);
    }

    @Override
    @Transactional(readOnly = true)
    public String showAllGenres() {
        return ui.showGenreList(genreDao.getAll());
    }

    @Override
    @Transactional(readOnly = true)
    public String getCommentsByBookId(int bookId) {
        return ui.showListOfComments(commentDao.getByBookId(bookId));
    }

    @Override
    @Transactional
    public String addCommentByBookId(int bookId) {
        final var newComment = input.getNewComment(bookId);
        final var comment = commentDao.save(newComment);
        return ui.showCommentInfo(comment);
    }
}
