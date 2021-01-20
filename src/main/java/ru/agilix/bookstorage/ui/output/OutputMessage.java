package ru.agilix.bookstorage.ui.output;

import de.vandermeer.asciitable.AsciiTable;

public class OutputMessage {
    public String renderMessage(String message) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(message);
        table.addRule();
        return table.render();
    }
}
