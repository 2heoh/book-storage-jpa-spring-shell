package ru.agilix.bookstorage.ui.output;

import de.vandermeer.asciitable.AsciiTable;
import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Author;

import java.util.List;

@Service
public class TextAuthorOutputService extends OutputMessage implements AuthorOutputService {

    @Override
    public String showAuthorsList(List<Author> list) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, "List of authors:");
        table.addRule();
        table.addRow("id", "name");
        table.addRule();
        for (Author author : list) {
            table.addRow(String.valueOf(author.getId()), author.getName());
        }
        table.addRule();
        return table.render();
    }

    @Override
    public String showEmptyAuthorsList() {
        return renderMessage("Authors not found.");
    }
}
