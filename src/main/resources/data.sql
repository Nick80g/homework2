
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS book_author CASCADE;


CREATE TABLE IF NOT EXISTS books (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(40) NOT NULL,
                                     author varchar(40) NOT NULL
    );
CREATE TABLE IF NOT EXISTS reader_book (
                                        reader_id BIGINT NOT NULL,
                                        book_id BIGINT NOT NULL
    );
CREATE TABLE IF NOT EXISTS reader (
                                           id BIGINT NOT NULL,
                                           name VARCHAR(40) NOT NULL,
                                           surname  VARCHAR(40) NOT NULL
);



INSERT INTO books (title, author) VALUES ('book', 'author');

INSERT INTO reader_book (reader_id, book_id) VALUES (1, 1);
INSERT INTO reader_book (reader_id, book_id) VALUES (2, 1);
INSERT INTO reader_book (reader_id, book_id) VALUES (3, 1);

INSERT INTO reader (id, name, surname) VALUES (1, 'Igor', 'Sev');
INSERT INTO reader (id, name, surname) VALUES (2, 'Roman', 'Rem');
INSERT INTO reader (id, name, surname) VALUES (3, 'Anton', 'Jon');
