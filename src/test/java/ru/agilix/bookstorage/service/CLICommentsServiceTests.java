package ru.agilix.bookstorage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.agilix.bookstorage.dao.BooksDao;
import ru.agilix.bookstorage.dao.CommentDao;
import ru.agilix.bookstorage.dao.dsl.BookBuilder;
import ru.agilix.bookstorage.dao.dsl.Create;
import ru.agilix.bookstorage.domain.Comment;
import ru.agilix.bookstorage.ui.output.CommentOutputService;

import java.text.ParseException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CLICommentsServiceTests {
    @Mock
    private CommentDao commentDao;

    private CommentsService service;

    @Mock
    private CommentOutputService output;

    @Mock
    private InputService input;

    @BeforeEach
    void setUp() {
        this.service = new CLICommentsService(commentDao, output, input);
    }



    @Test
    void shouldGetNewCommentAndSaveIt() throws ParseException {

        Comment comment = Create.Comment()
                .Id(0)
                .Text("text")
                .Author("somebody")
                .Date("2020-01-19 10:00:00")
                .build();

        given(input.getNewComment(1)).willReturn(comment);

        service.addCommentByBookId(1);

        verify(input, times(1)).getNewComment(1);
        verify(commentDao, times(1)).save(comment);
    }

    @Test
    void shouldCallDeleteOnDaoAndSuccessMessageWhenCommentExists() {
        final var existingId = 1;
        given(commentDao.getById(existingId)).willReturn(Create.Comment().Id(existingId).build());

        service.deleteComment(1);

        verify(commentDao, times(1)).delete(existingId);
        verify(output, times(1)).showCommentDeletedMessage(existingId);
    }

    @Test
    void shouldNotCallDeleteOnDaoAndShowErrorMessageWhenCommentNotExists() {
        int nonExistingId = -1;

        service.deleteComment(nonExistingId);

        verify(commentDao, times(0)).delete(nonExistingId);
        verify(output, times(1)).showCommentNotFound(nonExistingId);
    }

    @Test
    void shouldCallGetUpdatedCommentDaoSaveAndShowResultMessage() {
        final var existingCommentId = 1;
        Comment comment = Create.Comment().Id(existingCommentId).build();
        given(input.getUpdatedComment(comment)).willReturn(comment);
        given(commentDao.getById(existingCommentId)).willReturn(comment);

        service.updateComment(existingCommentId);

        verify(commentDao, times(1)).getById(existingCommentId);
        verify(input, times(1)).getUpdatedComment(comment);
        verify(commentDao, times(1)).save(any());
        verify(output, times(1)).showCommentUpdated(any());
    }

    @Test
    void shouldShowCommentIsNotFoundForNonExistingCommentId() {
        final var nonExistingCommentId = -1;
        Comment comment = Create.Comment().Id(nonExistingCommentId).build();
        given(commentDao.getById(nonExistingCommentId)).willReturn(null);

        service.updateComment(nonExistingCommentId);

        verify(commentDao, times(0)).save(comment);
        verify(output, times(1)).showCommentNotFound(nonExistingCommentId);

    }

}
