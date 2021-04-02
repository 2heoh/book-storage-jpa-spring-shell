package ru.agilix.bookstorage.dao;

import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
}
