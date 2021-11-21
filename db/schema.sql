CREATE TABLE post
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT
);
CREATE TABLE city
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);
INSERT INTO city(name) VALUES ('Москва');
INSERT INTO city(name) VALUES ('Санкт-Петербург');
INSERT INTO city(name) VALUES ('Екатеринбург');
INSERT INTO city(name) VALUES ('Сочи');
INSERT INTO city(name) VALUES ('Новосибирск');
INSERT INTO city(name) VALUES ('Саратов');

CREATE TABLE candidate
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    photo_id INTEGER,
    city_id  INTEGER REFERENCES city(id)
);
CREATE TABLE photo
(
    id    SERIAL PRIMARY KEY REFERENCES candidate(id) ON DELETE CASCADE,
    image bytea
);
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password TEXT
);


