package ru.agilix.bookstorage.ui.output;

public interface OutputTable {

    void header(String... columnNames);

    void row(String... cells);

    String render();
}
