--liquibase formatted sql

--changeset sergey.lobin:2021-01-19-007-comments
create table comments (
  id bigint IDENTITY NOT NULL PRIMARY KEY,
  text VARCHAR(255),
  author VARCHAR(255),
  book_id bigint,
  date DATETIME(6)
)