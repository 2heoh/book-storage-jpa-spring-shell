package ru.agilix.bookstorage.ui.output;

import lombok.val;
import org.springframework.stereotype.Service;
import ru.agilix.bookstorage.domain.Comment;

@Service
public class TextCommentOutputService implements CommentOutputService {

    @Override
    public String showCommentInfo(Comment comment) {
        val table = new OutputTwoColumnTable("New comment #" + comment.getId());
        table.row(
            String.format("%s said at %s", comment.getAuthor(), comment.getDate()),
            comment.getText()
        );
        return table.render();
    }

    @Override
    public String showCommentDeletedMessage(int id) {
        return new OutputMessage(String.format("Comment #%d successfully deleted", id)).render();
    }

    @Override
    public String showCommentNotFound(int id) {
        return new OutputMessage(String.format("Comment #%d is not found deleted", id)).render();
    }

    @Override
    public String showCommentUpdated(Comment comment) {
        val table = new OutputTwoColumnTable(
            String.format("Comment #%d successfully updated:",  comment.getId())
        );
        table.row(
                String.format("%s said at %s", comment.getAuthor(), comment.getDate()),
                comment.getText()
        );
        return table.render();
    }

}
