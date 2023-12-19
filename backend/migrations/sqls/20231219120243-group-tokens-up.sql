CREATE TABLE
  group_tokens (
    token_id SERIAL PRIMARY KEY,
    token text,
    created_by TEXT REFERENCES users(id),
    group_id INT REFERENCES groups(group_id),
    created_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(token)
  );

CREATE INDEX
  idx_token ON group_tokens USING btree(token);

CREATE INDEX
  idx_token_created_by ON group_tokens USING btree(created_by);

CREATE INDEX
  idx_token_group_id ON group_tokens USING btree(token);
  
CREATE INDEX idx_created_at ON group_tokens USING btree(created_at);