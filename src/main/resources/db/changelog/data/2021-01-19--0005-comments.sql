--liquibase formatted sql

--changeset sergey.lobin:2021-01-19-005-comments
insert into comments (`id`, `text`, `author`, `book_id`, `date`)
values (1, 'first', 'John Doe', 1, '2021-01-19 10:00:00'),
       (2, 'second', 'John Doe', 1, '2021-01-19 10:01:00'),
       (3, 'third', 'John Doe', 2, '2021-01-19 10:02:00');

