-- Таблица пользователей
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Таблица мероприятий
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    max_viewers INT DEFAULT 100,
    start_time TIMESTAMP DEFAULT NOW(),
    end_time TIMESTAMP,
    access_key VARCHAR(50) UNIQUE NOT NULL,
    host_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);

-- Таблица участников мероприятий
CREATE TABLE event_participants (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT REFERENCES events(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(255),
    joined_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (event_id, user_id)
);

-- Таблица сообщений чата
CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT REFERENCES events(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);