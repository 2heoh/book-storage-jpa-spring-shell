package ru.agilix.bookstorage.ui.output;

import de.vandermeer.asciitable.AsciiTable;
import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Genre;

import java.util.List;

@Service
public class TextGenreOutputService implements GenreOutputService {
    @Override
    public String showGenreList(List<Genre> genres) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, "List of genres:");
        table.addRule();
        table.addRow("id", "name");

        table.addRule();
        for (Genre author : genres) {
            table.addRow(String.valueOf(author.getId()), author.getName());
        }
        table.addRule();
        return table.render();

    }}
