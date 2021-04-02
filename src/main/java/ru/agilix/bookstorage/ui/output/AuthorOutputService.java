package ru.agilix.bookstorage.ui.output;

import ru.agilix.bookstorage.domain.Author;

import java.util.List;

public interface AuthorOutputService {
    String showAuthorsList(List<Author> list);

    String showEmptyAuthorsList();

}
