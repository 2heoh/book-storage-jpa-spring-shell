--liquibase formatted sql

--changeset sergey.lobin:2021-01-13-003-books
insert into books (`id`, `title`, `description`, `genre_id`)
values (1, 'Война и мир',
        '«Война́ и мир» — роман-эпопея Льва Николаевича Толстого, описывающий русское общество в эпоху войн против Наполеона в 1805—1812 годах. Эпилог романа доводит повествование до 1820 года.',
        2),
       (2, 'Вий', '', 2);

