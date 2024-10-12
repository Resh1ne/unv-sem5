CREATE TABLE exhibition_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50)
);

INSERT INTO exhibition_types (name)
VALUES
    ('VISUAL_ART'),
    ('APPLIED_ART'),
    ('SCULPTURE');

CREATE TABLE owner_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50)
);

INSERT INTO owner_types (name)
VALUES
    ('CITY_ORGANIZATION'),
    ('REGIONAL_ORGANIZATION'),
    ('PUBLIC_ORGANIZATION'),
    ('PRIVATE_INDIVIDUAL');

CREATE TABLE execution_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50)
);
INSERT INTO execution_types (name)
VALUES
    ('PAINTING'),
    ('WATERCOLOR'),
    ('SCULPTURE');

CREATE TABLE owners (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    type_id INT REFERENCES owner_types
);

CREATE TABLE exhibition_halls (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    area DECIMAL(10, 2),
    address VARCHAR(255),
    phone VARCHAR(20),
    owner_id INT REFERENCES owners
);

CREATE TABLE exhibitions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    hall_id INT REFERENCES exhibition_halls,
    type_id INT REFERENCES exhibition_types,
    start_date DATE,
    end_date DATE
);

CREATE TABLE artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_place VARCHAR(100),
    birth_date DATE,
    biography TEXT,
    education TEXT
);

CREATE TABLE artworks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    execution_id INT REFERENCES execution_types,
    creation_date DATE,
    height DECIMAL(10, 2),
    width DECIMAL(10, 2),
    volume DECIMAL(10, 2),  -- Только для скульптур
    artist_id INT REFERENCES artists
);

CREATE TABLE artwork_exhibitions (
    exhibition_id INT REFERENCES exhibitions,
    artwork_id INT REFERENCES artworks,
    PRIMARY KEY (exhibition_id, artwork_id)
);