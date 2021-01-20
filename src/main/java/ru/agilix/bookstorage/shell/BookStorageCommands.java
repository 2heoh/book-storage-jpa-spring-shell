package ru.agilix.bookstorage.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.agilix.bookstorage.service.AuthorsService;
import ru.agilix.bookstorage.service.BooksService;
import ru.agilix.bookstorage.service.CommentsService;
import ru.agilix.bookstorage.service.GenresService;

@ShellComponent
public class BookStorageCommands {
    private final BooksService books;
    private final CommentsService comments;
    private final AuthorsService authors;
    private final GenresService genres;

    public BookStorageCommands(BooksService books, CommentsService comments, AuthorsService authors, GenresService genres) {
        this.books = books;
        this.comments = comments;
        this.authors = authors;
        this.genres = genres;
    }

    @ShellMethod(value = "Displays list of entities (eg: book <id>, books all, comments <book_id>)")
    public String show(@ShellOption String first, @ShellOption String second) {
        if (first.equals("books") && second.equals("all")) {
            return books.retrieveAllBooks();
        } else if (first.equals("authors") && second.equals("all")) {
            return authors.showAllAuthors();
        } else if (first.equals("genres") && second.equals("all")) {
            return genres.showAllGenres();
        } else if (first.equals("book")) {
            return books.retrieveBook(Integer.parseInt(second));
        } else if (first.equals("comments")) {
            return comments.getCommentsByBookId(Integer.parseInt(second));
        }

        return "don't know: " + first;
    }

    @ShellMethod(value = "Adds new book or comment")
    public String add(@ShellOption String what, @ShellOption(defaultValue = "-1") int bookId) {
        if (what.equals("book")) {
            return books.createBook();
        } else if (what.equals("comment")) {
            if (bookId == -1) {
                return "please pass <book_id>";
            }
            return comments.addCommentByBookId(bookId);
        }

        return "don't know: " + what;
    }


    @ShellMethod(value = "Update book info")
    public String update(@ShellOption String what, @ShellOption int id) {
        if (what.equals("book")) {
            return books.updateBook(id);
        } else if (what.equals("comment")) {
            return comments.updateComment(id);
        } else {
            return "don't know: " + what;
        }
    }

    @ShellMethod(value = "Update book info")
    public String delete(@ShellOption String what, @ShellOption int id) {
        if (what.equals("book")) {
            return books.deleteBook(id);
        } else if (what.equals("comment")) {
            return comments.deleteComment(id);
        } else {
            return "don't know: " + what;
        }
    }

}
