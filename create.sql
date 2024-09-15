CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    status INTEGER NOT NULL,
    city VARCHAR(30) NOT NULL
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    color VARCHAR(30) NOT NULL,
    size INTEGER NOT NULL,
    city VARCHAR(30) NOT NULL
);

CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    city VARCHAR(30) NOT NULL
);

CREATE TABLE quantity (
    supplier_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL,
    count INTEGER NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

INSERT INTO suppliers(name, status, city) VALUES ('Петров', 20, 'Москва');
INSERT INTO suppliers(name, status, city) VALUES ('Синицин', 10, 'Таллинн');
INSERT INTO suppliers(name, status, city) VALUES ('Федоров', 30, 'Таллинн');
INSERT INTO suppliers(name, status, city) VALUES ('Чаянов', 20, 'Минск');
INSERT INTO suppliers(name, status, city) VALUES ('Крюков', 30, 'Киев');

INSERT INTO products(name, color, size, city) VALUES ('Болт', 'Красный', 12, 'Москва');
INSERT INTO products(name, color, size, city) VALUES ('Гайка', 'Зеленый', 17, 'Минск');
INSERT INTO products(name, color, size, city) VALUES ('Диск', 'Черный', 17, 'Вильнюс');
INSERT INTO products(name, color, size, city) VALUES ('Диск', 'Черный', 14, 'Москва');
INSERT INTO products(name, color, size, city) VALUES ('Корпус', 'Красный', 12, 'Минск');
INSERT INTO products(name, color, size, city) VALUES ('Крышки', 'Красный', 19, 'Москва');

INSERT INTO projects(name, city) VALUES ('ИПР1', 'Минск');
INSERT INTO projects(name, city) VALUES ('ИПР2', 'Таллинн');
INSERT INTO projects(name, city) VALUES ('ИПР3', 'Псков');
INSERT INTO projects(name, city) VALUES ('ИПР4', 'Псков');
INSERT INTO projects(name, city) VALUES ('ИПР5', 'Москва');
INSERT INTO projects(name, city) VALUES ('ИПР6', 'Саратов');
INSERT INTO projects(name, city) VALUES ('ИПР7', 'Москва');
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (1, 1, 1, 200);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (1, 1, 2, 700);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 3, 1, 400);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 2, 2, 200);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 3, 3, 200);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 3, 4, 500);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 3, 5, 600);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 3, 6, 400);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 3, 7, 800);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (2, 5, 2, 100);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (3, 3, 1, 200);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (3, 4, 2, 500);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (4, 6, 3, 300);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (4, 6, 7, 300);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 2, 2, 200);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 2, 4, 100);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 5, 5, 500);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 5, 7, 100);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 6, 2, 200);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 1, 2, 100);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 3, 4, 200);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 4, 4, 800);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 5, 4, 400);
INSERT INTO quantity(supplier_id, product_id, project_id, count) VALUES (5, 6, 4, 500);