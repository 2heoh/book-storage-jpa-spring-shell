package ru.agilix.bookstorage.dao.dsl;

import ru.agilix.bookstorage.dao.dsl.BookBuilder;

public class Create {
    public static BookBuilder Book() {
        return new BookBuilder();
    }

}
