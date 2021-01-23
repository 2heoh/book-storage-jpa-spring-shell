package ru.agilix.bookstorage.ui.output;

import de.vandermeer.asciitable.AsciiTable;
import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Comment;

import java.util.List;

@Service
public class TextCommentOutputService extends OutputMessage implements CommentOutputService {


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

    @Override
    public String showCommentDeletedMessage(int id) {
        return renderMessage(String.format("Comment #%d successfully deleted", id));
    }

    @Override
    public String showCommentNotFound(int id) {
        return renderMessage(String.format("Comment #%d is not found deleted", id));
    }

    @Override
    public String showCommentUpdated(Comment comment) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, String.format("Comment #%d successfully updated:",  comment.getId()));
        table.addRule();
        table.addRow(
                String.format("%s said at %s", comment.getAuthor(), comment.getDate()),
                comment.getText());
        table.addRule();
        return table.render();
    }

}
