CREATE TABLE users (
  id text,
  email text,
  picture text,
  given_name text,
  family_name text,
  email_verified boolean
);

CREATE INDEX idx_users_id ON users USING btree(id);
CREATE INDEX idx_users_email ON users USING btree(email);