package ru.agilix.bookstorage.ui;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.ui.output.CommentOutputService;
import ru.agilix.bookstorage.ui.output.TextCommentOutputService;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TextCommentsBookOutputServiceTest {
    private CommentOutputService ui;

    @BeforeEach
    void setUp() {
        ui = new TextCommentOutputService();
    }
    @Test
    void showListOfComments() throws ParseException {
        Comment first = Create.Comment()
                .Id(1)
                .Author("John Doe")
                .Text("first")
                .Date("2021-01-19 10:00:00")
                .build();

        Comment second = Create.Comment()
                .Id(2)
                .Author("John Doe")
                .Text("second")
                .Date("2021-01-19 11:00:00")
                .build();

        val comments = List.of(first, second);


        val result = ui.showListOfComments(comments);

        assertThat(result)
                .contains("Comments:")
                .contains("#1 'John Doe' said at 2021-01-19 10:00:00")
                .contains("first")
                .contains("#2 'John Doe' said at 2021-01-19 11:00:00")
                .contains("second");
    }

    @Test
    void shouldDisplayNewComment() throws ParseException {
        val comment = Create.Comment()
                .Id(1)
                .Text("text")
                .Author("author")
                .Date("2021-01-19 19:00:00")
                .build();

        final var result = ui.showCommentInfo(comment);

        assertThat(result)
                .contains("New comment")
                .contains("author said at 2021-01-19 19:00:00")
                .contains("text");
    }

    @Test
    void shouldDisplayCommentIsDeletedMessage() {

        final var result = ui.showCommentDeletedMessage(1);

        assertThat(result)
                .contains("Comment #1 successfully deleted");

    }

    @Test
    void shouldDisplayErrorForNonExitingId() {

        final var result = ui.showCommentNotFound(-1);

        assertThat(result)
                .contains("Comment #-1 is not found");

    }

    @Test
    void shouldDisplayCommentUpdated() throws ParseException {
        final var comment = Create.Comment()
                .Id(1)
                .Text("text")
                .Author("author")
                .build();

        final var result = ui.showCommentUpdated(comment);

        assertThat(result)
                .contains("Comment #1 successfully updated:")
                .contains("author")
                .contains("text");
    }
}
