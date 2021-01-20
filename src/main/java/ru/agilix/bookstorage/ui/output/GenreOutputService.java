package ru.agilix.bookstorage.ui.output;

import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

public interface GenreOutputService {
    String showGenreList(List<Genre> genres);
}
