package ru.agilix.bookstorage.ui;

import de.vandermeer.asciitable.AsciiTable;
import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Author;
import ru.agilix.bookstorage.domain.Book;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextOutputService implements MessageCreatorService {

    @Override
    public String showBooksList(List<Book> books) {

        if (books.isEmpty())
            return renderMessage("Books not found.");

        AsciiTable render = new AsciiTable();
        render.addRule();
        render.addRow(null, "List of books:");
        render.addRule();
        render.addRow("id", "title");
        render.addRule();
        for (Book book : books) {
            render.addRow(String.valueOf(book.getId()), book.getTitle());
        }
        render.addRule();

        return render.render();
    }

    @Override
    public String showBookInfo(Book book) {

        List<String> details = new ArrayList<>();
        if (!book.getAuthors().isEmpty()) {
            String authors = "by: ";
            for (Author author : book.getAuthors()) {
                authors += " [" + author.getName() + "] ";
            }
            details.add(authors);
        }
        if (book.getGenre() != null) {
            details.add("genre: " + book.getGenre().getName() + " ");
        }
        if (book.getDescription() == null || book.getDescription().equals("")) {
            details.add("description is not set");
        } else {
            details.add(book.getDescription());
        }
        return render(String.format("#%d %s", book.getId(), book.getTitle()), details);
    }

    private String render(String title, List<String> lines) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(title);
        table.addRule();
        for (String line : lines) {
            table.addRow(line);
            table.addRule();
        }
        return table.render();
    }

    private String renderMessage(String message) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(message);
        table.addRule();
        return table.render();
    }


    @Override
    public String showBookCreatedMessage(Book inserted) {
        return render("Created book:", List.of(String.format("#%d %s", inserted.getId(), inserted.getTitle())));
    }

    @Override
    public String showBookDeletedMessage(int id) {
        return renderMessage(String.format("Deleted book: #%d", id));
    }

    @Override
    public String showBookNotFound(int id) {
        return renderMessage(String.format("Book not found: #%d", id));
    }

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

    }

    @Override
    public String showListOfComments(List<Comment> comments) {

        String result = renderMessage("Comments:") + "\n";

        for (Comment comment : comments) {

            result += renderMessage(String.format(
                    "#%s '%s' said at %s: %s",
                    comment.getId(),
                    comment.getAuthor(),
                    comment.getDate(),
                    comment.getText())) + "\n";
        }

        return result;
    }

    @Override
    public String showCommentInfo(Comment comment) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, "New comment #" + comment.getId());
        table.addRule();
        table.addRow(
                String.format("%s said at %s", comment.getAuthor(), comment.getDate()),
                comment.getText());
        table.addRule();
        return table.render();
    }


}
