package ru.agilix.bookstorage.ui.output;

import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Author;

import java.util.List;

@Service
public class TextAuthorOutputService implements AuthorOutputService {

    @Override
    public String showAuthorsList(List<Author> list) {
        final var table = new OutputTwoColumnTable("List of authors:");
        table.header("id", "name");
        for (Author author : list) {
            table.row(String.valueOf(author.getId()), author.getName());
        }
        return table.render();
    }

    @Override
    public String showEmptyAuthorsList() {
        return new OutputMessage("Authors not found.").render();
    }
}
