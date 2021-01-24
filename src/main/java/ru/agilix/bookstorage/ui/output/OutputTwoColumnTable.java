package ru.agilix.bookstorage.ui.output;


import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.List;

public class OutputTwoColumnTable implements OutputTable {
    private final List<List<String>> rows = new ArrayList<>();

    public  OutputTwoColumnTable(String header) {
        rows.add(List.of(header));
    }

    @Override
    public void header(String... columnNames) {
        row(columnNames);
    }

    public void row(String... cells) {
        rows.add(List.of(cells[0], cells[1]));
    }

    public String render() {
        AsciiTable table = new AsciiTable();
        table.addRule();
        for ( List<String> row : rows) {
            if(row.size() == 1) {
                table.addRow(null, row.get(0));
            } else {
                table.addRow(row.get(0), row.get(1));
            }
            table.addRule();
        }
        return table.render();
    }
}
