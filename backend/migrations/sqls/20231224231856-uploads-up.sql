CREATE TABLE
  uploads (
    upload_id SERIAL PRIMARY KEY,
    user_id TEXT REFERENCES users(id),
    metadata TEXT,
    path TEXT,
    created_at TIMESTAMP DEFAULT now()
  );
  
CREATE INDEX idx_upload_path ON uploads USING btree(path);