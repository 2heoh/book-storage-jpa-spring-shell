package ru.agilix.bookstorage.dao;

import ru.agilix.bookstorage.domain.Book;

import java.util.List;

public interface BooksDao {
    List<Book> getAll();

    Book create(String title);

    Book getById(int id);

    void save(Book updatedBook);

    void delete(int id);
}
