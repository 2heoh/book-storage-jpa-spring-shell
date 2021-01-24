package ru.agilix.bookstorage.ui.output;

import lombok.val;
import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Genre;
import java.util.List;

@Service
public class TextGenreOutputService implements GenreOutputService {
    @Override
    public String showGenreList(List<Genre> genres) {
        val table = new OutputTwoColumnTable("List of genres:");
        table.row("id", "name");
        for (Genre author : genres) {
            table.row(String.valueOf(author.getId()), author.getName());
        }
        return table.render();
    }
}
