package ru.agilix.bookstorage.ui;

import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

@Service
public class ConsoleUpdateService implements UpdateService {

    private final IOService readerWriter;

    public ConsoleUpdateService(IOService readerWriter) {
        this.readerWriter = readerWriter;
    }

    @Override
    public String getNewValueFor(String field, String oldValue) {
        readerWriter.putString(String.format("Enter new %s [%s]: ", field, oldValue));
        return readerWriter.getString();
    }

    @Override
    public Author getNewAuthor(List<Author> authors) {
        for (Author author : authors) {
            readerWriter.putString(String.format("%d - %s", author.getId(), author.getName()));
        }
        readerWriter.putString("Enter new author id from list above: ");

        int authorId = Integer.parseInt(readerWriter.getString());

        if (authors.stream().noneMatch(a -> a.getId() == authorId))
            throw new NoSuchAuthor();

        return authors.stream().filter( a -> a.getId() == authorId).findFirst().get();
    }

    @Override
    public Genre getNewGenre(List<Genre> genres) {
        for (Genre genre : genres) {
            readerWriter.putString(String.format("%d - %s", genre.getId(), genre.getName()));
        }
        readerWriter.putString("Enter new genre id from list above: ");

        int genreId = Integer.parseInt(readerWriter.getString());

        return genres.stream().filter( g -> g.getId() == genreId).findFirst().get();
    }
}
