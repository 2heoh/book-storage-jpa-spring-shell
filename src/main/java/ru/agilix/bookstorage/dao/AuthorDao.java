package ru.agilix.bookstorage.dao;

import ru.agilix.bookstorage.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();
}
