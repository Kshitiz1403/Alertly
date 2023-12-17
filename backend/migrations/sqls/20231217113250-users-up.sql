ALTER TABLE users ADD CONSTRAINT unique_user_id UNIQUE(id);
ALTER TABLE users ADD COLUMN last_login timestamp DEFAULT now();