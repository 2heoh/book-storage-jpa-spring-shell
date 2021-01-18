--liquibase formatted sql

--changeset sergey.lobin:2021-01-11-002-authors
CREATE TABLE authors(
 id bigint IDENTITY NOT NULL PRIMARY KEY,
 name VARCHAR(255)
);