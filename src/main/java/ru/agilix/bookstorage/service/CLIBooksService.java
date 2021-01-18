package ru.agilix.bookstorage.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agilix.bookstorage.dao.AuthorDao;
import ru.agilix.bookstorage.dao.BooksDao;
import ru.agilix.bookstorage.dao.GenreDao;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.ui.UiService;

import java.util.List;

@Service
public class CLIBooksService implements BooksService {
    private final BooksDao booksDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final UiService ui;

    public CLIBooksService(BooksDao booksDao, UiService ui, AuthorDao authorDao, GenreDao genreDao) {
        this.booksDao = booksDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.ui = ui;
    }

    @Override
    public String updateBook(int id) {
        Book existingBook = booksDao.getById(id);
        Book updatedBook = ui.getUpdatedBookInfo(existingBook, authorDao.getAll(), genreDao.getAll());
        booksDao.save(updatedBook);
        return ui.showBookInfo(updatedBook);
    }

    @Override
    public String retrieveAllBooks() {
        List<Book> bookList = booksDao.getAll();
        return ui.showBooksList(bookList);
    }

    @Override
    public String createBook(String title) {
        Book inserted = booksDao.create(title);
        return ui.showBookCreatedMessage(inserted);
    }

    @Override
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
        try {
            booksDao.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return ui.showBookNotFound(id);
        }

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
}
