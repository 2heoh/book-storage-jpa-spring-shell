package ru.agilix.bookstorage.ui.output;

import de.vandermeer.asciitable.AsciiTable;

public class OutputMessage {
    private final String text;

    public OutputMessage(String text) {
        this.text = text;
    }

    public String render() {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(text);
        table.addRule();
        return table.render();
    }
}
