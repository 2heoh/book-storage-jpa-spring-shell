package ru.agilix.bookstorage.dao.dsl;

import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Genre;

import java.util.ArrayList;
import java.util.List;

public class BookBuilder {
    private String title = "no name // dsl";
    private int id = -1;
    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();
    private String description = "not set // dsl";

    public BookBuilder Title(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder Id(int id) {
        this.id = id;
        return this;
    }

    public BookBuilder Author(Author author) {
        this.authors.add(author);
        return this;
    }

    public BookBuilder Genre(Genre genre) {
        this.genres.add(genre);
        return this;
    }

    public BookBuilder Description(String description) {
        this.description = description;
        return this;
    }

    public Book build() {
        return new Book(id, title, description, authors, genres);
    }
}
