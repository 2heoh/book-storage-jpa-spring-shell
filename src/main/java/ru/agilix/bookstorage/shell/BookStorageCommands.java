package ru.agilix.bookstorage.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.agilix.bookstorage.service.BooksService;

@ShellComponent
public class BookStorageCommands {
    private final BooksService booksService;

    public BookStorageCommands(BooksService booksService) {
        this.booksService = booksService;
    }

    @ShellMethod(value = "Displays list of entities (eg: book <id>, books all, comments <book_id>)")
    public String show(@ShellOption String first, @ShellOption String second) {
        if (first.equals("books") && second.equals("all")) {
            return booksService.retrieveAllBooks();
        } else if (first.equals("authors") && second.equals("all")) {
            return booksService.showAllAuthors();
        } else if (first.equals("genres") && second.equals("all")) {
            return booksService.showAllGenres();
        } else if (first.equals("book")) {
            return booksService.retrieveBook(Integer.parseInt(second));
        } else if (first.equals("comments")) {
            return booksService.getCommentsByBookId(Integer.parseInt(second));
        }

        return "don't know: " + first;
    }

    @ShellMethod(value = "Adds new book or comment")
    public String add(@ShellOption String what, @ShellOption(defaultValue = "-1") int bookId) {
        if (what.equals("book")) {
            return booksService.createBook();
        } else if (what.equals("comment")) {
            if (bookId == -1) {
                return "please pass <book_id>";
            }
            return booksService.addCommentByBookId(bookId);
        }

        return "don't know: " + what;
    }


    @ShellMethod(value = "Update book info")
    public String update(@ShellOption String what, @ShellOption int id) {
        if (what.equals("book"))
            return booksService.updateBook(id);

        return "don't know: " + what;
    }

    @ShellMethod(value = "Update book info")
    public String delete(@ShellOption String what, @ShellOption int id) {
        if (what.equals("book"))
            return booksService.deleteBook(id);

        return "don't know: " + what;
    }

}
