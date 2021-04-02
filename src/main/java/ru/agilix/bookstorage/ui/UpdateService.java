package ru.agilix.bookstorage.ui;

import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

public interface UpdateService {
    String getNewValueFor(String field, String oldValue);

    List<Author> getNewAuthor(List<Author> authors);

    Genre getNewGenre(List<Genre> genres);
}
