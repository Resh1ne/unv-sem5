CREATE TABLE exhibition_types (
    id BIGSERIAL PRIMARY KEY,
    exhibition_type VARCHAR(50),
	CONSTRAINT unique_exhibition_type_name UNIQUE (exhibition_type)
);


CREATE TABLE owner_types (
    id BIGSERIAL PRIMARY KEY,
    owner_type VARCHAR(50),
	CONSTRAINT unique_owner_type_name UNIQUE (owner_type)
);


CREATE TABLE execution_types (
    id BIGSERIAL PRIMARY KEY,
    execution VARCHAR(50),
	CONSTRAINT unique_execution_type_name UNIQUE (execution)
);

CREATE TABLE owners (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    type_id INT REFERENCES owner_type
    CONSTRAINT fk_owner_type FOREIGN KEY (type_id) REFERENCES owner_types(id) ON DELETE CASCADE ON UPDATE CASCADE
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

CREATE INDEX idx_exhibition_dates ON exhibitions (start_date, end_date);

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
    artist_id INT REFERENCES artists,
    CONSTRAINT unique_artwork_per_artist UNIQUE (title, artist_id)
);

CREATE TABLE artwork_exhibitions (
    exhibition_id INT REFERENCES exhibitions,
    artwork_id INT REFERENCES artworks,
    PRIMARY KEY (exhibition_id, artwork_id)
);

-- Заполнение таблиц
INSERT INTO owner_types (owner_type)
VALUES
    ('CITY_ORGANIZATION'),
    ('REGIONAL_ORGANIZATION'),
    ('PUBLIC_ORGANIZATION'),
    ('PRIVATE_INDIVIDUAL');

INSERT INTO execution_types (execution)
VALUES
    ('PAINTING'),
    ('WATERCOLOR'),
    ('SCULPTURE');

INSERT INTO exhibition_types (exhibition_type)
VALUES
    ('VISUAL_ART'),
    ('APPLIED_ART'),
    ('SCULPTURE');

INSERT INTO owners (name, address, phone, type_id)
VALUES
    ('City Museum', '123 Main St', '123-456-7890', 1),
    ('Regional Art Center', '456 Elm St', '987-654-3210', 2),
    ('John Doe', '789 Pine St', '555-555-5555', 4);

INSERT INTO exhibition_halls (name, area, address, phone, owner_id)
VALUES
    ('Main Hall', 500.00, '123 Main St', '123-456-7890', 1),
    ('Small Hall', 200.50, '456 Elm St', '987-654-3210', 2),
    ('Private Gallery', 150.75, '789 Pine St', '555-555-5555', 3);

INSERT INTO exhibitions (name, hall_id, type_id, start_date, end_date)
VALUES
    ('Modern Art Exhibition', 1, 1, '2024-01-15', '2024-02-15'),
    ('Sculpture Showcase', 2, 3, '2024-03-01', '2024-03-30'),
    ('Watercolor Wonders', 3, 2, '2024-04-10', '2024-05-10');

INSERT INTO artists (name, birth_place, birth_date, biography, education)
VALUES
    ('Alice Smith', 'New York, USA', '1985-07-12', 'Contemporary painter specializing in abstract art.', 'Fine Arts, NYU'),
    ('Bob Johnson', 'London, UK', '1972-05-22', 'Sculptor known for large public installations.', 'Royal College of Art'),
    ('Carol Lee', 'Paris, France', '1990-09-15', 'Watercolor artist inspired by nature.', 'École des Beaux-Arts');

INSERT INTO artworks (title, execution_id, creation_date, height, width, volume, artist_id)
VALUES
    ('Abstract Sunrise', 1, '2023-06-01', 100.00, 150.00, NULL, 1),
    ('Modern Sculpture', 3, '2022-08-15', NULL, NULL, 300.00, 2),
    ('Nature in Watercolor', 2, '2024-02-20', 50.00, 70.00, NULL, 3);

INSERT INTO artwork_exhibitions (exhibition_id, artwork_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);