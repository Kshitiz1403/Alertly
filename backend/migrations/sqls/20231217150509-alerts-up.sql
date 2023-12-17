CREATE TABLE alerts (
    alert_id SERIAL PRIMARY KEY,
    group_id INT REFERENCES groups(group_id),
    user_id TEXT REFERENCES users(id),
    title TEXT NOT NULL,
    description TEXT CHECK (LENGTH(description)<=180),
    severity TEXT DEFAULT 'normal',
    sent_at TIMESTAMP DEFAULT now()
);