package ru.agilix.bookstorage.service;

import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.dao.AuthorDao;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.ui.output.AuthorOutputService;

import java.util.List;

@Service
public class CLIAuthorsService implements AuthorsService {
    private final AuthorDao authorDao;
    private final AuthorOutputService ui;

    public CLIAuthorsService(AuthorDao authorDao, AuthorOutputService ui) {
        this.ui = ui;
        this.authorDao = authorDao;
    }

    @Override
    public String showAllAuthors() {
        List<Author> authors = authorDao.getAll();

        if (authors.isEmpty())
            return ui.showEmptyAuthorsList();

        return ui.showAuthorsList(authors);
    }

}
