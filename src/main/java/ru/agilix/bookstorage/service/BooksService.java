package ru.agilix.bookstorage.service;

public interface BooksService {
    String createBook(String title);

    String retrieveBook(int id);

    String updateBook(int id);

    String retrieveAllBooks();

    String deleteBook(int id);

    String showAllAuthors();

    String showAllGenres();
}
