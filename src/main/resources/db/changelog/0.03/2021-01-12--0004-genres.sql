--liquibase formatted sql

--changeset sergey.lobin:2021-01-12-004-genres
CREATE TABLE genres(
 id bigint IDENTITY NOT NULL PRIMARY KEY,
 name VARCHAR(255)
);