package ru.agilix.bookstorage.ui.output;

import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.List;

public class OutputOneColumnTable implements OutputTable {

    private final List<String> rows = new ArrayList<>();

    public OutputOneColumnTable(String header) {
        rows.add(header);
    }

    @Override
    public void header(String... columnNames) {
        row(columnNames);
    }

    @Override
    public void row(String... cells) {
        rows.add(cells[0]);
    }

    @Override
    public String render() {
        AsciiTable table = new AsciiTable();
        table.addRule();
        for (String row : rows) {
            table.addRow(row);
            table.addRule();
        }
        return table.render();
    }
}
