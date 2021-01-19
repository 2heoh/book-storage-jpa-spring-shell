package ru.agilix.bookstorage.dao;

import ru.agilix.bookstorage.domain.Book;

import java.util.List;

public interface BooksDao {
    List<Book> getAll();

    Book save(Book book);

    Book getById(int id);

    void delete(int id);
}
