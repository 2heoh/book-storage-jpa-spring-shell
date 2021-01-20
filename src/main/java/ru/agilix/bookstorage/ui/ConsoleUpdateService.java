package ru.agilix.bookstorage.ui;

import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Genre;

import java.util.ArrayList;
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

        final var newValue = readerWriter.getString();
        if (newValue.equals("")) {
            return oldValue;
        }
        return newValue;
    }

    @Override
    public List<Author> getNewAuthor(List<Author> authors) {
        for (Author author : authors) {
            readerWriter.putString(String.format("%d - %s", author.getId(), author.getName()));
        }
        readerWriter.putString("Enter new author (or authors separated by coma) id from list above: ");

        List<Author> result = new ArrayList<>();
        for (String id : readerWriter.getString().split(",")) {
            final var authorId = Integer.parseInt(id);

            for (Author author: authors) {
                if (author.getId() == authorId) {
                    result.add(author);
                }
            }
        }

        if (result.isEmpty())
            throw new NoSuchAuthor();

        return result;
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
