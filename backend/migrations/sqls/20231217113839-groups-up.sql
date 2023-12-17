CREATE TABLE
  groups (
    group_id SERIAL PRIMARY KEY,
    group_name TEXT UNIQUE NOT NULl,
    description TEXT,
    created_at TIMESTAMP DEFAULT now()
  );

CREATE TABLE
  user_groups(
    user_id TEXT REFERENCES users(id),
    group_id INT REFERENCES groups(group_id),
    is_admin BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (user_id, group_id),
    created_at TIMESTAMP DEFAULT now()
  );
