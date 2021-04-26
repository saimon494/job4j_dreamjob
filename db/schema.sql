CREATE TABLE post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);
CREATE TABLE candidate
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    photo_id INTEGER
);
CREATE TABLE photo
(
    id    SERIAL PRIMARY KEY REFERENCES candidate (id) ON DELETE CASCADE,
    image bytea
);
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password TEXT
);
-- TRUNCATE TABLE post RESTART IDENTITY;
-- TRUNCATE TABLE candidate RESTART IDENTITY cascade;
-- TRUNCATE TABLE photo RESTART IDENTITY;
-- TRUNCATE TABLE users RESTART IDENTITY;

